package com.LMS.LMS.Testing.parameterized;

import com.LMS.LMS.Testing.model.Book;
import com.LMS.LMS.Testing.repository.BookRepo;
import com.LMS.LMS.Testing.service.implementation.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookParameterizedTest {

    @Autowired
    private BookRepo bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll(); // Clean up the repository before each test
    }

    @ParameterizedTest
    @CsvSource({
            "The Great Gatsby, F. Scott Fitzgerald, true",
            "1984, George Orwell, false",
            "To Kill a Mockingbird, Harper Lee, true"
    })
    public void testCreateAndFindBook(String title, String author, boolean isAvailable) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCheckOut(isAvailable);

        Book createdBook = bookService.createBook(book);
        assertNotNull(createdBook);
        assertEquals(title, createdBook.getTitle());
        assertEquals(author, createdBook.getAuthor());
        assertEquals(isAvailable, createdBook.isCheckOut());

        Book foundBook = bookService.findBookById(createdBook.getBook_id());
        assertNotNull(foundBook);
        assertEquals(title, foundBook.getTitle());
        assertEquals(author, foundBook.getAuthor());
        assertEquals(isAvailable, foundBook.isCheckOut());
    }
}
