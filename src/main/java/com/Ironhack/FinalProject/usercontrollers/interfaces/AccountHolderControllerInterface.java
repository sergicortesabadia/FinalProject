package com.Ironhack.FinalProject.usercontrollers.interfaces;

import com.Ironhack.FinalProject.DTOs.*;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderControllerInterface {
    BigDecimal transferMoneyByAccountType(UserDetails userDetails, TransferDTO transferDTO);
    List<Account> showAllAccountsByAccountHolder(UserDetails userDetails);
    Money getBalance(UserDetails userDetails, Long accountNumber);
    AccountHolder createUserAccount(AccountHolderCreationDTO accountHolderCreationDTO);
    AccountHolder createAddressAsUser(UserDetails userDetails, AddressDTO addressDTO);
    AccountHolder createMailingAddressAsUser(UserDetails userDetails, AddressDTO addressDTO);
    User changePassword(UserDetails userDetails, ChangePassWordDTO changePassWordDTO);

}
