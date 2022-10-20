package com.Ironhack.FinalProject.transactions.transactionservice;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.repositories.AccountRepository;
import com.Ironhack.FinalProject.repositories.TransactionRepository;
import com.Ironhack.FinalProject.transactions.Transaction;
import com.Ironhack.FinalProject.transactions.transactionservice.transactionserviceinterface.TransactionServiceInterface;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;

@Service
public class TransactionService implements TransactionServiceInterface {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    public Account detectFraudSecond(Long accountNumber, Transaction transaction){
        Account account = accountRepository.findById(accountNumber).get();
        if(!accountRepository.findById(accountNumber).get().getSentTransactionList().isEmpty()) {
            Transaction oldTransaction = accountRepository.findById(accountNumber).get().getSentTransactionList().get(-1);

            if(transaction.getTransferDate().toLocalDate() == oldTransaction.getTransferDate().toLocalDate() && transaction.getTransferDate().toLocalTime().getHour() == oldTransaction.getTransferDate().toLocalTime().getHour() && transaction.getTransferDate().toLocalTime().getMinute() == oldTransaction.getTransferDate().toLocalTime().getMinute() && transaction.getTransferDate().toLocalTime().getSecond() - oldTransaction.getTransferDate().toLocalTime().getSecond() < 1) {
            account.setAccountStatus(AccountStatus.FROZEN);
        }
        }
        return accountRepository.save(account);
    }
}
