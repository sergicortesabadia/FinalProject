package com.Ironhack.FinalProject.userservices;
import com.Ironhack.FinalProject.DTOs.AccountHolderDTO;
import com.Ironhack.FinalProject.DTOs.TransferDTO;
import com.Ironhack.FinalProject.accountmodels.*;
import com.Ironhack.FinalProject.accountservices.interfaces.CheckingAccountServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.CreditCardServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.SavingsServiceInterface;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.enums.TransactionType;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.transactions.Transaction;
import com.Ironhack.FinalProject.transactions.transactionservice.transactionserviceinterface.TransactionServiceInterface;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.userservices.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    public Money getBalance(Long accountHolderId, Long accountNumber) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Account account = accountRepository.findById(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
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
    public List<Account> showAllAccountsByAccountHolder(Long accountHolderId) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Account> accountList = new ArrayList<>();
        accountList.addAll(accountHolder.getPrimaryAccountHolderList());
        accountList.addAll(accountHolder.getSecondaryAccountHolderList());
        return accountList;
    }

    public BigDecimal transferMoneyByAccountType(Long senderID, Long senderAccountId, BigDecimal transfer, Long receiverAccountId, Long receiverId) {
        Account senderAccount = accountRepository.findById(senderAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if (senderAccount.getAccountStatus() == AccountStatus.FROZEN) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        Account receiverAccount = accountRepository.findById(receiverAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        AccountHolder sender = accountHolderRepository.findById(senderID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        AccountHolder receiver = accountHolderRepository.findById(receiverId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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
        if (sent.getAmount().compareTo(senderSavings.getMinimumBalance().getAmount()) < 0) {
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
    public AccountHolder createUserAccount(AccountHolder accountHolder){
        userRepository.save(accountHolder);
        return accountHolderRepository.save(accountHolder);
    }
}