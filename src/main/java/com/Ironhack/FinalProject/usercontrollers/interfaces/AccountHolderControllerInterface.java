package com.Ironhack.FinalProject.usercontrollers.interfaces;

import com.Ironhack.FinalProject.DTOs.AccountDTO;
import com.Ironhack.FinalProject.DTOs.SecondaryOwnerDTO;
import com.Ironhack.FinalProject.DTOs.TransferDTO;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderControllerInterface {
    BigDecimal transferMoneyByAccountType(@RequestBody TransferDTO transferDTO);
    List<Account> showAllAccountsByAccountHolder(Long accountHolderId);
    Money getBalance(SecondaryOwnerDTO secondaryOwnerDTO);
    AccountHolder createUserAccount(AccountHolder accountHolder);
}
