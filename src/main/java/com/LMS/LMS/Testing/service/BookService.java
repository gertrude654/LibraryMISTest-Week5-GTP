package com.LMS.LMS.Testing.service;

import com.LMS.LMS.Testing.model.Book;

import java.util.List;

public interface BookService {

    Book findBookById(int id);

    List<Book> findAllBooks();

    Book createBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(int id);

    Book checkOutBook(int bookId);

    Book returnBook(int bookId);
}
