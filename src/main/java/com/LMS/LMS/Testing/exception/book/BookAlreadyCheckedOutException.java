package com.LMS.LMS.Testing.exception.book;

public class BookAlreadyCheckedOutException extends RuntimeException {
    public BookAlreadyCheckedOutException(String message) {
        super(message);
    }
}
