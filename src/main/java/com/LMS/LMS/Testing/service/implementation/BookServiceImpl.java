package com.LMS.LMS.Testing.service.implementation;

import com.LMS.LMS.Testing.exception.book.BookAlreadyCheckedOutException;
import com.LMS.LMS.Testing.exception.book.BookAlreadyExistsException;
import com.LMS.LMS.Testing.exception.book.BookNotCheckedOutException;
import com.LMS.LMS.Testing.exception.book.BookNotFoundException;
import com.LMS.LMS.Testing.repository.BookRepo;
import com.LMS.LMS.Testing.service.BookService;



import com.LMS.LMS.Testing.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepository;

    @Override
    public Book findBookById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        return optionalBook.get();
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (bookRepository.existsById(book.getBook_id())) {
            throw new BookAlreadyExistsException("Book with ID: " + book.getBook_id() + " already exists");
        }

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        if (book == null || book.getBook_id() <= 0) {
            throw new IllegalArgumentException("Invalid book data");
        }

        if (!bookRepository.existsById(book.getBook_id())) {
            throw new BookNotFoundException("Book not found with ID: " + book.getBook_id());
        }

        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
    }

    @Override
    public Book checkOutBook(int bookId) {
        Book book = findBookById(bookId);

        if (book.isCheckOut()) {
            throw new BookAlreadyCheckedOutException("Book with ID: " + bookId + " is already checked out");
        }

        book.setCheckOut(true);
        return bookRepository.save(book);
    }

    @Override
    public Book returnBook(int bookId) {
        Book book = findBookById(bookId);

        if (!book.isCheckOut()) {
            throw new BookNotCheckedOutException("Book with ID: " + bookId + " is not checked out");
        }

        book.setCheckOut(false);
        return bookRepository.save(book);
    }
}
