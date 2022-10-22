package com.Ironhack.FinalProject.userservices.interfaces;

import com.Ironhack.FinalProject.DTOs.AccountHolderCreationDTO;
import com.Ironhack.FinalProject.DTOs.AddressDTO;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderServiceInterface {
    Money getBalance(String username, Long accountNumber);
    List<Account> showAllAccountsByAccountHolder(String username);
    BigDecimal transferMoneyByAccountType(String username, Long senderAccountId, BigDecimal transfer, Long receiverAccountId, Long receiverId);
    BigDecimal transferFromSavings(AccountHolder sender, AccountHolder receiver, Savings senderSavings, BigDecimal transfer, Account receiverAccount, Money sent, Money received);
    BigDecimal transferFromCheckingAccount(AccountHolder sender, AccountHolder receiver, CheckingAccount senderCheckingAccount, BigDecimal transfer, Account receiverAccount, Money sent, Money received);
    AccountHolder createUserAccount(AccountHolderCreationDTO accountHolderCreationDTO);
    AccountHolder createAddressAsUser(String username, AddressDTO addressDTO);
    AccountHolder createMailingAddressAsUser(String username, AddressDTO addressDTO);
    User changePassword(String username, String oldPassword, String newPassword, String repeatPassword);
}
