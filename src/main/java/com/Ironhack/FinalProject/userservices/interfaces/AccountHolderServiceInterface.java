package com.Ironhack.FinalProject.userservices.interfaces;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderServiceInterface {
    Money getBalance(Long accountHolderId, Long accountNumber);
    List<Account> showAllAccountsByAccountHolder(Long accountHolderId);
    BigDecimal transferMoneyByAccountType(Long senderId, Long senderAccountId, BigDecimal transfer, Long receiverAccountId, Long receiverId);
    BigDecimal transferFromSavings(AccountHolder sender, AccountHolder receiver, Savings senderSavings, BigDecimal transfer, Account receiverAccount, Money sent, Money received);
    BigDecimal transferFromCheckingAccount(AccountHolder sender, AccountHolder receiver, CheckingAccount senderCheckingAccount, BigDecimal transfer, Account receiverAccount, Money sent, Money received);
    AccountHolder createUserAccount(AccountHolder accountHolder);
}
