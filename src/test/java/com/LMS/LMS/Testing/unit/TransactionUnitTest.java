package com.LMS.LMS.Testing.unit;

import com.LMS.LMS.Testing.exception.transaction.TransactionNotFoundException;
import com.LMS.LMS.Testing.model.Book;
import com.LMS.LMS.Testing.model.Patron;
import com.LMS.LMS.Testing.model.Transaction;
import com.LMS.LMS.Testing.repository.TransactionRepo;
import com.LMS.LMS.Testing.service.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionUnitTest {

    @Mock
    private TransactionRepo transactionRepository;

    @InjectMocks
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

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTransactionById() throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setPatron(patron1);
        transaction.setBook(book1);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.findTransactionById(1);
        assertNotNull(foundTransaction);
        assertEquals(1, foundTransaction.getPatron());
        assertEquals(1, foundTransaction.getBook());
    }

    @Test
    public void testFindTransactionByIdNotFound() {
        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.findTransactionById(1));
    }

    @Test
    public void testCreateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setPatron(patron1);
        transaction.setBook(book1);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        assertNotNull(createdTransaction);
        assertEquals(1, createdTransaction.getPatron());
        assertEquals(1, createdTransaction.getBook());
    }

    @Test
    public void testUpdateTransaction() throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setPatron(patron1);
        transaction.setBook(book1);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transaction.setReturned(true);
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);

        assertNotNull(updatedTransaction);
        assertTrue(updatedTransaction.isReturned());
    }

    @Test
    public void testUpdateTransactionNotFound() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);

        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.updateTransaction(transaction));
    }

    @Test
    public void testDeleteTransactionById() throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).deleteById(1);

        transactionService.deleteTransactionById(1);
        verify(transactionRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteTransactionByIdNotFound() {
        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.deleteTransactionById(1));
        verify(transactionRepository, never()).deleteById(anyInt());
    }
}
