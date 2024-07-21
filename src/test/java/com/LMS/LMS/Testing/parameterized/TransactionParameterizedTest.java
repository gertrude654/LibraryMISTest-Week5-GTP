package com.LMS.LMS.Testing.parameterized;

import com.LMS.LMS.Testing.exception.transaction.TransactionNotFoundException;
import com.LMS.LMS.Testing.model.Book;
import com.LMS.LMS.Testing.model.Patron;
import com.LMS.LMS.Testing.model.Transaction;
import com.LMS.LMS.Testing.repository.TransactionRepo;
import com.LMS.LMS.Testing.service.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionParameterizedTest  {

    @Mock
    private TransactionRepo transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, '2024-07-20', '2024-08-03', false",
            "2, 2, 2, '2024-07-21', '2024-08-04', true"
    })
    public void testFindTransactionById(int id, Patron patronId, Book bookId, String transactionDate, String dueDate, boolean isReturned) throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);
        transaction.setPatron(patronId);
        transaction.setBook(bookId); ;
        transaction.setTransactionDate(LocalDate.parse(transactionDate));
        transaction.setDueDate(LocalDate.parse(dueDate));
        transaction.setReturned(isReturned);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.findTransactionById(id);
        assertNotNull(foundTransaction);
        assertEquals(patronId, foundTransaction.getPatron());
        assertEquals(bookId, foundTransaction.getBook());
        assertEquals(LocalDate.parse(transactionDate), foundTransaction.getTransactionDate());
        assertEquals(LocalDate.parse(dueDate), foundTransaction.getDueDate());
        assertEquals(isReturned, foundTransaction.isReturned());
    }

    @ParameterizedTest
    @CsvSource({
            "1, '2024-07-20', '2024-08-03', false, true",
            "2, '2024-07-21', '2024-08-04', true, false"
    })
    public void testCreateTransaction(int id, String transactionDate, String dueDate, boolean isReturned, boolean saveResult) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);
        transaction.setTransactionDate(LocalDate.parse(transactionDate));
        transaction.setDueDate(LocalDate.parse(dueDate));
        transaction.setReturned(isReturned);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        assertNotNull(createdTransaction);
        assertEquals(transactionDate, createdTransaction.getTransactionDate().toString());
        assertEquals(dueDate, createdTransaction.getDueDate().toString());
        assertEquals(isReturned, createdTransaction.isReturned());
    }

    @ParameterizedTest
    @CsvSource({
            "1, '2024-07-20', '2024-08-03', false",
            "2, '2024-07-21', '2024-08-04', true"
    })
    public void testUpdateTransaction(int id, String transactionDate, String dueDate, boolean isReturned) throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);
        transaction.setTransactionDate(LocalDate.parse(transactionDate));
        transaction.setDueDate(LocalDate.parse(dueDate));
        transaction.setReturned(isReturned);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transaction.setReturned(!isReturned); // Toggle returned status
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);

        assertNotNull(updatedTransaction);
        assertEquals(!isReturned, updatedTransaction.isReturned());
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2"
    })
    public void testDeleteTransactionById(int id) throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).deleteById(id);

        transactionService.deleteTransactionById(id);
        verify(transactionRepository, times(1)).deleteById(id);
    }
}
