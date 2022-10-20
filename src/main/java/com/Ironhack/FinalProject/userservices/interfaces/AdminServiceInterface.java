package com.Ironhack.FinalProject.userservices.interfaces;

import com.Ironhack.FinalProject.DTOs.AccountDTO;
import com.Ironhack.FinalProject.DTOs.AccountHolderDTO;
import com.Ironhack.FinalProject.DTOs.CreateAccountDTO;
import com.Ironhack.FinalProject.DTOs.ModifyBalanceDTO;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.Ironhack.FinalProject.usermodels.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminServiceInterface {
    Account addBalance(ModifyBalanceDTO modifyBalanceDTO);
    Account decreaseBalance(ModifyBalanceDTO modifyBalanceDTO);
    Account changeAccountStatus(Long accountNumber, AccountStatus accountStatus);
    Account createNewUserAndAccount(AccountHolderDTO accountHolderDTO);
    void deleteAccount(Long accountNumber);
    List<Account> getAllAccounts();
    List<User> getAllUsers();
    Account createNewAccount(CreateAccountDTO createAccountDTO);
    Account assignSecondaryOwner (Long accountNumber, Long accountHolderId);
    Admin createAdmin (Admin admin);
    ThirdParty createThirdParty(ThirdParty thirdParty);
}
