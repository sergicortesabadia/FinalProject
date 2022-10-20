package com.Ironhack.FinalProject.usercontrollers;

import com.Ironhack.FinalProject.DTOs.AccountDTO;
import com.Ironhack.FinalProject.DTOs.SecondaryOwnerDTO;
import com.Ironhack.FinalProject.DTOs.TransferDTO;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usercontrollers.interfaces.AccountHolderControllerInterface;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.userservices.AccountHolderService;
import com.Ironhack.FinalProject.userservices.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountHolderController implements AccountHolderControllerInterface {
    @Autowired
    AccountHolderServiceInterface accountHolderServiceInterface;

    @PatchMapping("/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BigDecimal transferMoneyByAccountType(@RequestBody TransferDTO transferDTO){
        return accountHolderServiceInterface.transferMoneyByAccountType(transferDTO.getSenderId(), transferDTO.getSenderAccountNumber(), transferDTO.getTransfer(), transferDTO.getReceiverAccountNumber(), transferDTO.getReceiverId());
    }
    @GetMapping("/show_account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalance(SecondaryOwnerDTO secondaryOwnerDTO){
        return accountHolderServiceInterface.getBalance(secondaryOwnerDTO.getAccountHolderId(), secondaryOwnerDTO.getAccountNumber());
    }
    @GetMapping("/user/show_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> showAllAccountsByAccountHolder(Long accountHolderId){
        return accountHolderServiceInterface.showAllAccountsByAccountHolder(accountHolderId);
    }
    @PostMapping("/user/create_user")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createUserAccount(@RequestBody AccountHolder accountHolder){
        return accountHolderServiceInterface.createUserAccount(accountHolder);
    }

}
