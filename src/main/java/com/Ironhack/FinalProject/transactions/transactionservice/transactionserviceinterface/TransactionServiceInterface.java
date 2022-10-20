package com.Ironhack.FinalProject.transactions.transactionservice.transactionserviceinterface;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.transactions.Transaction;

public interface TransactionServiceInterface {
    Account detectFraudSecond(Long accountNumber, Transaction transaction);
}
