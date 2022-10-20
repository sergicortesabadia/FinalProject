package com.Ironhack.FinalProject.userservices;

import com.Ironhack.FinalProject.DTOs.AccountDTO;
import com.Ironhack.FinalProject.DTOs.AccountHolderDTO;
import com.Ironhack.FinalProject.DTOs.CreateAccountDTO;
import com.Ironhack.FinalProject.DTOs.ModifyBalanceDTO;
import com.Ironhack.FinalProject.accountmodels.*;
import com.Ironhack.FinalProject.accountservices.interfaces.CheckingAccountServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.CreditCardServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.SavingsServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.StudentCheckingAccountServiceInterface;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.Ironhack.FinalProject.usermodels.User;
import com.Ironhack.FinalProject.userservices.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingsServiceInterface savingsServiceInterface;
    @Autowired
    CreditCardServiceInterface creditCardServiceInterface;
    @Autowired
    CheckingAccountServiceInterface checkingAccountServiceInterface;
    @Autowired
    StudentCheckingAccountServiceInterface studentCheckingAccountServiceInterface;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;


public Account addBalance(ModifyBalanceDTO modifyBalanceDTO){
    Account account = accountRepository.findById(modifyBalanceDTO.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    if(modifyBalanceDTO.getAmount().compareTo(java.math.BigDecimal.ZERO) < 0) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Must be a positive number");
    Money money = new Money(account.getBalance().increaseAmount(modifyBalanceDTO.getAmount()));
    account.setBalance(money);
    return accountRepository.save(account);
}
public Account decreaseBalance(ModifyBalanceDTO modifyBalanceDTO){
        Account account = accountRepository.findById(modifyBalanceDTO.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(modifyBalanceDTO.getAmount().compareTo(java.math.BigDecimal.ZERO) < 0) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Must be a positive number");
        Money money = new Money(account.getBalance().decreaseAmount(modifyBalanceDTO.getAmount()));
        account.setBalance(money);
        return accountRepository.save(account);
    }

    public Account changeAccountStatus(Long accountNumber, AccountStatus accountStatus){
    Account account = accountRepository.findById(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    account.setAccountStatus(accountStatus);
    return accountRepository.save(account);
    }

    public Account createNewUserAndAccount(AccountHolderDTO accountHolderDTO){
    AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(), accountHolderDTO.getMail(), accountHolderDTO.getPhone(), accountHolderDTO.getBirthDate(), accountHolderDTO.getPrimaryAddress());
  //  userRepository.save(accountHolder);
    accountHolderRepository.save(accountHolder);
    String accountType = accountHolderDTO.getAccountType();
    BigDecimal initialBalance = accountHolderDTO.getInitialBalance();
    while(true){
        switch(accountType){
            case "savingsAccount":
                return savingsServiceInterface.createSavings(accountHolder, initialBalance);
            case "creditCard":
                return creditCardServiceInterface.createCreditCard(accountHolder, initialBalance);
            case "checkingAccount":
                if (LocalDate.now().compareTo(accountHolder.getBirthDate()) >= 24){
                return checkingAccountServiceInterface.createCheckingAccount(accountHolder, initialBalance);
                } else {
                    return studentCheckingAccountServiceInterface.createStudentCheckingAccount(accountHolder, initialBalance);
                }
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account type");
        }
    }
    }


    public void deleteAccount(Long accountNumber){
    accountRepository.findById(accountNumber).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    accountRepository.deleteById(accountNumber);
        System.out.println( "The account with the number "+accountNumber+" has been deleted");
    }

    public List<Account> getAllAccounts(){
    return accountRepository.findAll();
    }

    public List<User> getAllUsers(){
    return userRepository.findAll();
    }

    public Account createNewAccount(CreateAccountDTO createAccountDTO){
    AccountHolder accountHolder = accountHolderRepository.findById(createAccountDTO.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    BigDecimal initialBalance = createAccountDTO.getInitialBalance();
        while(true){
            switch(createAccountDTO.getAccountType()){
                case "savingsAccount":
                    return savingsServiceInterface.createSavings(accountHolder, initialBalance);
                case "creditCard":
                    return creditCardServiceInterface.createCreditCard(accountHolder, initialBalance);
                case "checkingAccount":
                    if (LocalDate.now().EPOCH.getYear() - accountHolder.getBirthDate().EPOCH.getYear() >= 24){
                        return checkingAccountServiceInterface.createCheckingAccount(accountHolder, initialBalance);
                    } else {
                        return studentCheckingAccountServiceInterface.createStudentCheckingAccount(accountHolder, initialBalance);
                    }
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account type");
            }

        }
    }

    public Account assignSecondaryOwner (Long accountNumber, Long accountHolderId){
    Account account = accountRepository.findById(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    account.setSecondaryOwner(accountHolder);
    return accountRepository.save(account);
    }
    public Admin createAdmin (Admin admin){
    return adminRepository.save(admin);
    }
    public ThirdParty createThirdParty(ThirdParty thirdParty){
    return thirdPartyRepository.save(thirdParty);
    }

}
