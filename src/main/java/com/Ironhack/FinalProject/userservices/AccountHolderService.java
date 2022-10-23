package com.Ironhack.FinalProject.userservices;
import com.Ironhack.FinalProject.DTOs.AccountHolderCreationDTO;
import com.Ironhack.FinalProject.DTOs.AddressDTO;
import com.Ironhack.FinalProject.accountmodels.*;
import com.Ironhack.FinalProject.accountservices.interfaces.CheckingAccountServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.CreditCardServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.SavingsServiceInterface;
import com.Ironhack.FinalProject.embeddables.Address;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.enums.TransactionType;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.roles.Role;
import com.Ironhack.FinalProject.roles.RolesEnum;
import com.Ironhack.FinalProject.transactions.Transaction;
import com.Ironhack.FinalProject.transactions.transactionservice.transactionserviceinterface.TransactionServiceInterface;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.User;
import com.Ironhack.FinalProject.userservices.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHolderService implements AccountHolderServiceInterface {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    SavingsServiceInterface savingsServiceInterface;
    @Autowired
    CreditCardServiceInterface creditCardServiceInterface;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;
    @Autowired
    TransactionServiceInterface transactionServiceInterface;
    @Autowired
    CheckingAccountServiceInterface checkingAccountServiceInterface;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Money getBalance(String username, Long accountNumber) {
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Account account = accountRepository.findById(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if(!accountHolder.getPrimaryAccountHolderList().contains(account) && !accountHolder.getSecondaryAccountHolderList().contains(account)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (account.getPrimaryOwner() == accountHolder || account.getSecondaryOwner() == accountHolder){
            if(savingsRepository.existsById(accountNumber)) {
                return savingsServiceInterface.addInterestRateOnSavings(accountNumber);
            }
            if(creditCardRepository.existsById(accountNumber)) {
                return creditCardServiceInterface.addInterestRateOnCreditCard(accountNumber);
            }
            if(checkingAccountRepository.existsById(accountNumber)){
                return checkingAccountServiceInterface.payMonthlyFee(accountNumber);
            }
            return account.getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed access to this account");
        }
    }
    public List<Account> showAllAccountsByAccountHolder(String username) {
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if(accountHolder.getPrimaryAccountHolderList().isEmpty() && accountHolder.getSecondaryAccountHolderList().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"You have no accounts");
        List<Account> accountList = new ArrayList<>();
        accountList.addAll(accountHolder.getPrimaryAccountHolderList());
        accountList.addAll(accountHolder.getSecondaryAccountHolderList());
        return accountList;
    }

    public BigDecimal transferMoneyByAccountType(String username, Long senderAccountId, BigDecimal transfer, Long receiverAccountId, Long receiverId) {
        Account senderAccount = accountRepository.findById(senderAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if (senderAccount.getAccountStatus() == AccountStatus.FROZEN) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        Account receiverAccount = accountRepository.findById(receiverAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        AccountHolder sender = accountHolderRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if(!sender.getPrimaryAccountHolderList().contains(senderAccount) && !sender.getSecondaryAccountHolderList().contains(senderAccount)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        AccountHolder receiver = accountHolderRepository.findById(receiverId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if(!receiver.getPrimaryAccountHolderList().contains(receiverAccount) && !receiver.getSecondaryAccountHolderList().contains(receiverAccount)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Money sent = new Money(senderAccount.getBalance().decreaseAmount(transfer));
        BigDecimal zero = new BigDecimal(0);
        if (senderAccount.getBalance().getAmount().subtract(transfer).compareTo(zero) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds");
        Money received = new Money(receiverAccount.getBalance().increaseAmount(transfer));
        if (checkingAccountRepository.existsById(senderAccountId)) {
            CheckingAccount senderCheckingAccount = checkingAccountRepository.findById(senderAccountId).get();
            return transferFromCheckingAccount(sender, receiver, senderCheckingAccount, transfer, receiverAccount, sent, received);
        } else if (savingsRepository.existsById(senderAccountId)) {
            Savings senderSavings = savingsRepository.findById(senderAccountId).get();
            return transferFromSavings(sender, receiver, senderSavings, transfer, receiverAccount, sent, received);
        } else if(creditCardRepository.existsById(senderAccountId)) {
            CreditCard senderCreditCard = creditCardRepository.findById(senderAccountId).get();
            return transferFromCreditCard(sender, receiver, senderCreditCard, transfer, receiverAccount, sent, received);
        } else {
            StudentCheckingAccount studentCheckingAccount = studentCheckingAccountRepository.findById(senderAccountId).get();
            return transferStudentCheckingAccount(sender, receiver, studentCheckingAccount, transfer, receiverAccount, sent, received);
        }
    }
    public BigDecimal transferFromSavings(AccountHolder sender, AccountHolder receiver, Savings senderSavings, BigDecimal transfer, Account receiverAccount, Money sent, Money received) {
        if (sent.getAmount().compareTo(senderSavings.getMinimumBalance().getAmount()) == -1) {
            sent.decreaseAmount(senderSavings.getPenaltyFee().getAmount());
        }
        senderSavings.setBalance(sent);
        receiverAccount.setBalance(received);
        savingsRepository.save(senderSavings);
        accountRepository.save(receiverAccount);
        Transaction newTransaction = new Transaction(TransactionType.SAVINGS, sender.getId(), receiver.getId(), transfer, LocalDateTime.now(), senderSavings, receiverAccount);
        transactionRepository.save(newTransaction);
        transactionServiceInterface.detectFraudSecond(sender.getId(), newTransaction);
        return senderSavings.getBalance().getAmount();
    }

    public BigDecimal transferFromCheckingAccount( AccountHolder sender, AccountHolder receiver, CheckingAccount senderCheckingAccount, BigDecimal transfer, Account receiverAccount, Money sent, Money received) {
        if (sent.getAmount().compareTo(senderCheckingAccount.getMinimumBalance().getAmount()) < 0) {
            sent.decreaseAmount(senderCheckingAccount.getPenaltyFee().getAmount());
        }
        senderCheckingAccount.setBalance(sent);
        receiverAccount.setBalance(received);
        checkingAccountRepository.save(senderCheckingAccount);
        accountRepository.save(receiverAccount);
        Transaction newTransaction = new Transaction(TransactionType.SAVINGS, sender.getId(), receiver.getId(), transfer, LocalDateTime.now(), senderCheckingAccount, receiverAccount);
        transactionRepository.save(newTransaction);
        transactionServiceInterface.detectFraudSecond(sender.getId(), newTransaction);
        return senderCheckingAccount.getBalance().getAmount();
    }
    public BigDecimal transferFromCreditCard( AccountHolder sender, AccountHolder receiver, CreditCard senderCreditCard, BigDecimal transfer, Account receiverAccount, Money sent, Money received) {
        senderCreditCard.setBalance(sent);
        receiverAccount.setBalance(received);
        creditCardRepository.save(senderCreditCard);
        accountRepository.save(receiverAccount);
        Transaction newTransaction = new Transaction(TransactionType.SAVINGS, sender.getId(), receiver.getId(), transfer, LocalDateTime.now(), senderCreditCard, receiverAccount);
        transactionRepository.save(newTransaction);
        transactionServiceInterface.detectFraudSecond(sender.getId(), newTransaction);
        return senderCreditCard.getBalance().getAmount();
    }
    public BigDecimal transferStudentCheckingAccount( AccountHolder sender, AccountHolder receiver, StudentCheckingAccount senderStudentCheckingAccount, BigDecimal transfer, Account receiverAccount, Money sent, Money received) {
        senderStudentCheckingAccount.setBalance(sent);
        receiverAccount.setBalance(received);
        studentCheckingAccountRepository.save(senderStudentCheckingAccount);
        accountRepository.save(receiverAccount);
        Transaction newTransaction = new Transaction(TransactionType.SAVINGS, sender.getId(), receiver.getId(), transfer, LocalDateTime.now(), senderStudentCheckingAccount, receiverAccount);
        transactionRepository.save(newTransaction);
        transactionServiceInterface.detectFraudSecond(sender.getId(), newTransaction);
        return senderStudentCheckingAccount.getBalance().getAmount();
    }
    public AccountHolder createUserAccount(AccountHolderCreationDTO accountHolderCreationDTO){
        Address address = new Address(accountHolderCreationDTO.getStreet(), accountHolderCreationDTO.getCity(), accountHolderCreationDTO.getPostalCode(), accountHolderCreationDTO.getProvinceState(), accountHolderCreationDTO.getCountry());
        AccountHolder accountHolder = new AccountHolder(accountHolderCreationDTO.getUsername(), passwordEncoder.encode("1234"), accountHolderCreationDTO.getMail(), accountHolderCreationDTO.getPhone(), accountHolderCreationDTO.getName(), accountHolderCreationDTO.getBirthDate(), address);
        accountHolder.addRole(new Role(RolesEnum.ACCOUNT_HOLDER, accountHolder));
        accountHolderRepository.save(accountHolder);
        roleRepository.save(new Role(RolesEnum.ADMIN, accountHolder));
        return accountHolder;
    }
    public AccountHolder createAddressAsUser(String username, AddressDTO addressDTO){
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Address address = new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getPostalCode(), addressDTO.getProvinceState(), addressDTO.getCountry());
        accountHolder.setPrimaryAddress(address);
        return accountHolderRepository.save(accountHolder);
    }
    public AccountHolder createMailingAddressAsUser(String username, AddressDTO addressDTO){
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Address address = new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getPostalCode(), addressDTO.getProvinceState(), addressDTO.getCountry());
        accountHolder.setMailingAddress(address);
        return accountHolderRepository.save(accountHolder);
    }
    public User changePassword(String username, String oldPassword, String newPassword, String repeatPassword){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
        if(!newPassword.equals(repeatPassword)) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The repeated password is different from the first entry");
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}