package com.paypal.transaction_service.services;

import com.paypal.transaction_service.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    List<Transaction>getAllTransactions();
}
