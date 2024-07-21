package com.LMS.LMS.Testing.exception.book;


public class BookNotCheckedOutException extends RuntimeException {
    public BookNotCheckedOutException(String message) {
        super(message);
    }
}
