package com.Ironhack.FinalProject.usercontrollers;

import com.Ironhack.FinalProject.DTOs.*;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usercontrollers.interfaces.AccountHolderControllerInterface;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.userservices.AccountHolderService;
import com.Ironhack.FinalProject.userservices.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountHolderController implements AccountHolderControllerInterface {
    @Autowired
    AccountHolderServiceInterface accountHolderServiceInterface;

    @PatchMapping("/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BigDecimal transferMoneyByAccountType(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TransferDTO transferDTO){
        return accountHolderServiceInterface.transferMoneyByAccountType(userDetails.getUsername(), transferDTO.getSenderAccountNumber(), transferDTO.getTransfer(), transferDTO.getReceiverAccountNumber(), transferDTO.getReceiverId());
    }
    @GetMapping("/show_account/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long accountNumber){
        return accountHolderServiceInterface.getBalance(userDetails.getUsername(), accountNumber);
    }
    @GetMapping("/user/show_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> showAllAccountsByAccountHolder(@AuthenticationPrincipal UserDetails userDetails){
        return accountHolderServiceInterface.showAllAccountsByAccountHolder(userDetails.getUsername());
    }
    @PostMapping("/new_address")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAddressAsUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddressDTO addressDTO){
        return accountHolderServiceInterface.createAddressAsUser(userDetails.getUsername(), addressDTO);
    }
    @PostMapping("/new_mailing_address")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createMailingAddressAsUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddressDTO addressDTO){
        return accountHolderServiceInterface.createAddressAsUser(userDetails.getUsername(), addressDTO);
    }
    @PostMapping("/user/create_user")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createUserAccount(@RequestBody AccountHolderCreationDTO accountHolderCreationDTO){
        return accountHolderServiceInterface.createUserAccount(accountHolderCreationDTO);
    }

}
