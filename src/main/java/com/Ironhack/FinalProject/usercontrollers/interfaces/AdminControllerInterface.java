package com.Ironhack.FinalProject.usercontrollers.interfaces;

import com.Ironhack.FinalProject.DTOs.*;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.Ironhack.FinalProject.usermodels.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminControllerInterface {
    Account addBalance(ModifyBalanceDTO modifyBalanceDTO);
    Account decreaseBalance(ModifyBalanceDTO modifyBalanceDTO);
    Account createNewUserAndAccount(AccountHolderCreationDTO accountHolderCreationDTO);
    Account changeAccountStatus(AccountDTO accountDTO);
    void deleteAccount(Long accountNumber);
    List<User> getAllUsers();
    List<Account> getAllAccounts();
    Account createNewAccount(CreateAccountDTO createAccountDTO);
    Account assignSecondaryOwner (SecondaryOwnerDTO secondaryOwnerDTO);
    AccountHolder createUserAccount(AccountHolderCreationDTO accountHolderCreationDTO);
    Admin createAdmin (Admin admin);
    ThirdParty createThirdParty(ThirdParty thirdParty);
    AccountHolder createAddress(AddressDTO addressDTO);
    AccountHolder createMailingAddress(AddressDTO addressDTO);
    User changePasswordAsAdmin(UserDetails userDetails, ChangePassWordDTO changePassWordDTO);

}

