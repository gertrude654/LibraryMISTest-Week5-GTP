package com.LMS.LMS.Testing.service;


import com.LMS.LMS.Testing.exception.transaction.TransactionNotFoundException;
import com.LMS.LMS.Testing.model.Transaction;


import java.util.List;

public interface TransactionService {

    Transaction findTransactionById(int id) throws TransactionNotFoundException;

    List<Transaction> findAllTransactions();

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction) throws TransactionNotFoundException;

    void deleteTransactionById(int id) throws TransactionNotFoundException;
}
