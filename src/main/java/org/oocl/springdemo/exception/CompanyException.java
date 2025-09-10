package org.oocl.springdemo.exception;

public class CompanyException extends RuntimeException {
    private final String message;
    public CompanyException(String message) {
        this.message = message;
    }
}
