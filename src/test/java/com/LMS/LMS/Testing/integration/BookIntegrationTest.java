package com.LMS.LMS.Testing.integration;


import com.LMS.LMS.Testing.exception.book.BookAlreadyExistsException;
import com.LMS.LMS.Testing.exception.book.BookNotFoundException;
import com.LMS.LMS.Testing.model.Book;
import com.LMS.LMS.Testing.repository.BookRepo;
import com.LMS.LMS.Testing.service.implementation.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class BookIntegrationTest {

    @Autowired
    private BookRepo bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll(); // Clean up the repository before each test
    }

    @Test
    public void testCreateAndFindBook() {
        Book book = new Book();
        //book.setBook_id(1);
        book.setIsbn("12345");
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setPublication_date(LocalDate.now());
        book.setCategory("Learning");
        book.setCheckOut(true);

        Book createdBook = bookService.createBook(book);
        assertNotNull(createdBook);
        assertEquals("The Great Gatsby", createdBook.getTitle());

        Book foundBook = bookService.findBookById(createdBook.getBook_id());
        assertNotNull(foundBook);
        assertEquals("F. Scott Fitzgerald", foundBook.getAuthor());
    }



    @Test
    public void testUpdateBook() {
        Book book = new Book();

        book.setIsbn("12345");
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublication_date(LocalDate.now());
        book.setCategory("Learning");
        book.setCheckOut(true);

        Book createdBook = bookService.createBook(book);
        createdBook.setTitle("Animal Farm");
        bookService.updateBook(createdBook);

        Book updatedBook = bookService.findBookById(createdBook.getBook_id());
        assertNotNull(updatedBook);
        assertEquals("Animal Farm", updatedBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();

        //book.setBook_id(1);
        book.setIsbn("12345");
        book.setTitle("To Kill a Mockingbird");
        book.setAuthor("Harper Lee");
        book.setPublication_date(LocalDate.now());
        book.setCategory("Learning");
        book.setCheckOut(true);



        Book createdBook = bookService.createBook(book);
        bookService.deleteBookById(createdBook.getBook_id());

        assertThrows(BookNotFoundException.class, () -> bookService.findBookById(createdBook.getBook_id()));
    }
    @Test
    public void testCheckoutBook() {
        Book book = new Book();

        //book.setBook_id(1);
        book.setIsbn("12345");
        book.setTitle("The Catcher in the Rye");
        book.setAuthor("J.D. Salinger");
        book.setPublication_date(LocalDate.now());
        book.setCategory("Learning");
        book.setCheckOut(true);

        Book createdBook = bookService.createBook(book);
        assertTrue(createdBook.isCheckOut());

        // Perform checkout
        bookService.checkOutBook(createdBook.getBook_id());
        Book checkedOutBook = bookService.findBookById(createdBook.getBook_id());
        assertNotNull(checkedOutBook);
        assertFalse(checkedOutBook.isCheckOut());
    }

    @Test
    public void testCheckoutBookNotAvailable() {
        Book book = new Book();

        //book.setBook_id(1);
        book.setIsbn("12345");
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setPublication_date(LocalDate.now());
        book.setCategory("Learning");

        book.setCheckOut(false); // Book is not available for checkout

        Book createdBook = bookService.createBook(book);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.checkOutBook(createdBook.getBook_id()));
    }
}
