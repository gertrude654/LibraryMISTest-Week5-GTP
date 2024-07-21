package com.LMS.LMS.Testing.service.implementation;

import com.LMS.LMS.Testing.exception.transaction.TransactionNotFoundException;
import com.LMS.LMS.Testing.repository.TransactionRepo;
import com.LMS.LMS.Testing.service.TransactionService;
import com.LMS.LMS.Testing.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepository;

    @Override
    public Transaction findTransactionById(int id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + id));
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) throws TransactionNotFoundException {
        if (!transactionRepository.existsById(transaction.getTransactionId())) {
            throw new TransactionNotFoundException("Transaction not found with ID: " + transaction.getTransactionId());
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransactionById(int id) throws TransactionNotFoundException {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
