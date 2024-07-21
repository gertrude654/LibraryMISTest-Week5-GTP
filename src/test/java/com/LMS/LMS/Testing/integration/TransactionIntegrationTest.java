package com.LMS.LMS.Testing.integration;


import com.LMS.LMS.Testing.exception.transaction.TransactionNotFoundException;
import com.LMS.LMS.Testing.model.Book;
import com.LMS.LMS.Testing.model.Patron;
import com.LMS.LMS.Testing.model.Transaction;
import com.LMS.LMS.Testing.repository.TransactionRepo;
import com.LMS.LMS.Testing.service.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TransactionIntegrationTest {

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private TransactionServiceImpl transactionService;

    private Book book1;
    private Patron patron1;

    @BeforeEach
    public void setUp() {
        patron1 = new Patron();
        patron1.setPatron_id(1);
        patron1.setFirstName("Alice");
        patron1.setLastName("Smith");

        book1 = new Book();
        book1.setBook_id(1);
        book1.setIsbn("12345");
        book1.setAuthor("James");
        book1.setTitle("Java Programming");
        book1.setPublication_date(LocalDate.now());
        book1.setCategory("Learning");
        book1.setCheckOut(true);

        transactionRepository.deleteAll(); // Clean up the repository before each test
    }

    @Test
    public void testCreateAndFindTransaction() {
        Transaction transaction = new Transaction();

        transaction.setPatron(patron1);
        transaction.setBook(book1);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        assertNotNull(createdTransaction);
        assertEquals(1, createdTransaction.getPatron().getPatron_id());
        assertEquals(1, createdTransaction.getBook().getBook_id());

        Transaction foundTransaction = transactionService.findTransactionById(createdTransaction.getTransactionId());
        assertNotNull(foundTransaction);
        assertEquals(1, foundTransaction.getPatron().getPatron_id());
        assertEquals(1, foundTransaction.getBook().getBook_id());
    }

    @Test
    public void testUpdateTransaction() {
        Transaction transaction = new Transaction();

        transaction.setPatron(patron1);
        transaction.setBook(book1);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        createdTransaction.setReturned(true);
        transactionService.updateTransaction(createdTransaction);

        Transaction updatedTransaction = transactionService.findTransactionById(createdTransaction.getTransactionId());
        assertNotNull(updatedTransaction);
        assertTrue(updatedTransaction.isReturned());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction transaction = new Transaction();

        transaction.setPatron(patron1);
        transaction.setBook(book1);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        transactionService.deleteTransactionById(createdTransaction.getTransactionId());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.findTransactionById(createdTransaction.getTransactionId()));
    }
}
