package com.Ironhack.FinalProject.usercontrollers;

import com.Ironhack.FinalProject.DTOs.*;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.usercontrollers.interfaces.AdminControllerInterface;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.Ironhack.FinalProject.usermodels.User;
import com.Ironhack.FinalProject.userservices.AdminService;
import com.Ironhack.FinalProject.userservices.interfaces.AccountHolderServiceInterface;
import com.Ironhack.FinalProject.userservices.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController implements AdminControllerInterface {
    @Autowired
    AdminServiceInterface adminServiceInterface;
    @Autowired
    AccountHolderServiceInterface accountHolderServiceInterface;

    @PatchMapping("/add_balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account addBalance(@RequestBody ModifyBalanceDTO modifyBalanceDTO){
        return adminServiceInterface.addBalance(modifyBalanceDTO);
    }
    @PatchMapping("/decrease_balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account decreaseBalance(@RequestBody ModifyBalanceDTO modifyBalanceDTO){
        return adminServiceInterface.decreaseBalance((modifyBalanceDTO));
    }

    @PostMapping("/create_user_account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewUserAndAccount(@RequestBody AccountHolderCreationDTO accountHolderCreationDTO){
        return adminServiceInterface.createNewUserAndAccount(accountHolderCreationDTO);
    }

    @PostMapping("/create_account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewAccount(@RequestBody CreateAccountDTO createAccountDTO){
        return adminServiceInterface.createNewAccount(createAccountDTO);
    }

    @PatchMapping("/change_status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account changeAccountStatus(@RequestBody AccountDTO accountDTO){
        return adminServiceInterface.changeAccountStatus(accountDTO.getAccountNumber(), accountDTO.getAccountStatus());
    }

    @DeleteMapping("/delete_account/{accountNumber}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAccount(@PathVariable Long accountNumber){
        adminServiceInterface.deleteAccount(accountNumber);
    }

    @GetMapping("/show_users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return adminServiceInterface.getAllUsers();
    }

    @GetMapping("/show_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts(){
        return adminServiceInterface.getAllAccounts();
    }

    @PatchMapping("/assign_secondary_owner")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account assignSecondaryOwner (@RequestBody SecondaryOwnerDTO secondaryOwnerDTO){
        return adminServiceInterface.assignSecondaryOwner(secondaryOwnerDTO.getAccountNumber(), secondaryOwnerDTO.getAccountHolderId());
    }
    @PostMapping("/create_user")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createUserAccount(@RequestBody AccountHolderCreationDTO accountHolderCreationDTO){
        return accountHolderServiceInterface.createUserAccount(accountHolderCreationDTO);
    }
    @PostMapping("/create_admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin (@RequestBody Admin admin){
        return adminServiceInterface.createAdmin(admin);
    }

    @PostMapping("/create_third_party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty){
        return adminServiceInterface.createThirdParty(thirdParty);
    }
@PostMapping("/create_address")
@ResponseStatus(HttpStatus.CREATED)
   public AccountHolder createAddress(@RequestBody AddressDTO addressDTO){
        return adminServiceInterface.createAddress(addressDTO);
}


    @PostMapping("/create_mailing_address")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createMailingAddress(@RequestBody AddressDTO addressDTO){
        return adminServiceInterface.createMailingAddress(addressDTO);
    }
}
