package com.LMS.LMS.Testing.exception.patron;

public class PatronAlreadyExistsException extends RuntimeException {
    public PatronAlreadyExistsException(String message) {
        super(message);
    }
}