package com.LMS.LMS.Testing.unit;

import com.LMS.LMS.Testing.exception.book.BookAlreadyCheckedOutException;
import com.LMS.LMS.Testing.exception.book.BookAlreadyExistsException;
import com.LMS.LMS.Testing.exception.book.BookNotCheckedOutException;
import com.LMS.LMS.Testing.exception.book.BookNotFoundException;
import com.LMS.LMS.Testing.model.Book;
import com.LMS.LMS.Testing.repository.BookRepo;
import com.LMS.LMS.Testing.service.implementation.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookUnitTest {

    @Mock
    private BookRepo bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindBookByIdThrowsExceptionForInvalidId() {
        // Method should throw IllegalArgumentException for invalid ID
        assertThrows(IllegalArgumentException.class, () -> bookService.findBookById(-1));
    }

    @Test
    public void testFindBookByIdThrowsExceptionForNonExistentBook() {
        // Mock behavior for non-existent book
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // Method should throw BookNotFoundException for non-existent book
        assertThrows(com.LMS.LMS.Testing.exception.book.BookNotFoundException.class, () -> bookService.findBookById(1));
    }

    @Test
    public void testCreateBookThrowsExceptionForNullBook() {
        // Method should throw IllegalArgumentException for null book
        assertThrows(IllegalArgumentException.class, () -> bookService.createBook(null));
    }

    @Test
    public void testCreateBookThrowsExceptionForExistingBook() {
        Book book = new Book();
        book.setBook_id(1);

        when(bookRepository.existsById(1)).thenReturn(true);

        // Method should throw BookAlreadyExistsException for existing book
        assertThrows(BookAlreadyExistsException.class, () -> bookService.createBook(book));
    }

    @Test
    public void testUpdateBookThrowsExceptionForInvalidBook() {
        Book book = new Book();
        book.setBook_id(-1);

        // Method should throw IllegalArgumentException for invalid book data
        assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(book));
    }

    @Test
    public void testUpdateBookThrowsExceptionForNonExistentBook() {
        Book book = new Book();
        book.setBook_id(1);
        when(bookRepository.existsById(1)).thenReturn(false);

        // Method should throw BookNotFoundException for non-existent book
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(book));
    }

    @Test
    public void testDeleteBookByIdThrowsExceptionForInvalidId() {
        // Method should throw IllegalArgumentException for invalid ID
        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBookById(-1));
    }

    @Test
    public void testDeleteBookByIdThrowsExceptionForNonExistentBook() {
        when(bookRepository.existsById(1)).thenReturn(false);

        // Method should throw BookNotFoundException for non-existent book
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(1));
    }

    @Test
    public void testCheckOutBookThrowsExceptionForAlreadyCheckedOutBook() {
        Book book = new Book();
        book.setBook_id(1);
        book.setCheckOut(true);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Method should throw BookAlreadyCheckedOutException for already checked out book
        assertThrows(BookAlreadyCheckedOutException.class, () -> bookService.checkOutBook(1));
    }

    @Test
    public void testReturnBookThrowsExceptionForNotCheckedOutBook() {
        Book book = new Book();
        book.setBook_id(1);
        book.setCheckOut(false);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Method should throw BookNotCheckedOutException for book not checked out
        assertThrows(BookNotCheckedOutException.class, () -> bookService.returnBook(1));
    }

    @Test
    public void testFindAllBooks() {
        Book book1 = new Book();
        book1.setBook_id(1);
        book1.setIsbn("12345");
        book1.setAuthor("James");
        book1.setTitle("Java Programming");
        book1.setPublication_date(LocalDate.now());
        book1.setCategory("Learning");
        book1.setCheckOut(true);

        Book book2 = new Book();
        book2.setBook_id(2);
        book2.setIsbn("123451345");
        book2.setAuthor("Miller");
        book2.setTitle("Karoke");
        book2.setPublication_date(LocalDate.now());
        book2.setCategory("Music");
        book2.setCheckOut(true);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> foundBooks = bookService.findAllBooks();
        assertEquals(2, foundBooks.size());
        assertEquals("Java Programming", foundBooks.get(0).getTitle());
        assertEquals("Karoke", foundBooks.get(1).getTitle());
    }
}




//
//import com.LMS.LMS.Testing.exception .*;
//import com.LMS.LMS.Testing.model.Book;
//import com.LMS.LMS.Testing.repository.BookRepo;
//import com.LMS.LMS.Testing.service.implementation.BookServiceImpl;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//import java.util.Arrays;
//import java.util.List;
//
//
//import static org.mockito.Mockito .*;
//
//public class BookUnitTest {
//
//        @Mock
//        private BookRepo bookRepository;
//
//        @InjectMocks
//        private BookServiceImpl bookService;
//
//        @Before
//        public void setUp() {
//            MockitoAnnotations.initMocks(this);
//        }
//
//        @Test(expected = IllegalArgumentException.class)
//        public void testFindBookByIdThrowsExceptionForInvalidId() {
//            // Method should throw IllegalArgumentException for invalid ID
//            bookService.findBookById(-1);
//        }
//
//        @Test(expected = BookNotFoundException.class)
//        public void testFindBookByIdThrowsExceptionForNonExistentBook() {
//            // Mock behavior for non-existent book
//            when(bookRepository.findById(1)).thenReturn(Optional.empty());
//
//            // Method should throw BookNotFoundException for non-existent book
//            bookService.findBookById(1);
//        }
//
//        @Test(expected = IllegalArgumentException.class)
//        public void testCreateBookThrowsExceptionForNullBook() {
//            // Method should throw IllegalArgumentException for null book
//            bookService.createBook(null);
//        }
//
//        @Test(expected = BookAlreadyExistsException.class)
//        public void testCreateBookThrowsExceptionForExistingBook() {
//            Book book = new Book();
//            book.setBook_id(1);
//            when(bookRepository.existsById(1)).thenReturn(true);
//
//            // Method should throw BookAlreadyExistsException for existing book
//            bookService.createBook(book);
//        }
//
//        @Test(expected = IllegalArgumentException.class)
//        public void testUpdateBookThrowsExceptionForInvalidBook() {
//            Book book = new Book();
//            book.setBook_id(-1);
//
//            // Method should throw IllegalArgumentException for invalid book data
//            bookService.updateBook(book);
//        }
//
//        @Test(expected = BookNotFoundException.class)
//        public void testUpdateBookThrowsExceptionForNonExistentBook() {
//            Book book = new Book();
//            book.setBook_id(1);
//            when(bookRepository.existsById(1)).thenReturn(false);
//
//            // Method should throw BookNotFoundException for non-existent book
//            bookService.updateBook(book);
//        }
//
//        @Test(expected = IllegalArgumentException.class)
//        public void testDeleteBookByIdThrowsExceptionForInvalidId() {
//            // Method should throw IllegalArgumentException for invalid ID
//            bookService.deleteBookById(-1);
//        }
//
//        @Test(expected = BookNotFoundException.class)
//        public void testDeleteBookByIdThrowsExceptionForNonExistentBook() {
//            when(bookRepository.existsById(1)).thenReturn(false);
//
//            // Method should throw BookNotFoundException for non-existent book
//            bookService.deleteBookById(1);
//        }
//
//        @Test(expected = BookAlreadyCheckedOutException.class)
//        public void testCheckOutBookThrowsExceptionForAlreadyCheckedOutBook() {
//            Book book = new Book();
//            book.setBook_id(1);
//            book.setCheckedOut(true);
//
//            when(bookRepository.findById(1)).thenReturn(Optional.of(book));
//
//            // Method should throw BookAlreadyCheckedOutException for already checked out book
//            bookService.checkOutBook(1);
//        }
//
//        @Test(expected = BookNotCheckedOutException.class)
//        public void testReturnBookThrowsExceptionForNotCheckedOutBook() {
//            Book book = new Book();
//            book.setBookId(1);
//            book.setCheckedOut(false);
//
//            when(bookRepository.findById(1)).thenReturn(Optional.of(book));
//
//            // Method should throw BookNotCheckedOutException for book not checked out
//            bookService.returnBook(1);
//        }
//
//        @Test
//        public void testFindAllBooks() {
//            Book book1 = new Book();
//            book1.setBookId(1);
//            book1.setTitle("Title1");
//            book1.setAuthor("Author1");
//
//            Book book2 = new Book();
//            book2.setBookId(2);
//            book2.setTitle("Title2");
//            book2.setAuthor("Author2");
//
//            List<Book> books = Arrays.asList(book1, book2);
//
//            when(bookRepository.findAll()).thenReturn(books);
//
//            List<Book> foundBooks = bookService.findAllBooks();
//            assertEquals(2, foundBooks.size());
//            assertEquals("Title1", foundBooks.get(0).getTitle());
//            assertEquals("Title2", foundBooks.get(1).getTitle());
//        }
//    }
//}
