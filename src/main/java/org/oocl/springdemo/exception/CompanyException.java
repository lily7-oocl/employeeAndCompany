package org.oocl.springdemo.exception;

import org.oocl.springdemo.common.CompanyErrorStatus;

public class CompanyException extends RuntimeException {
    private final String message;
    private final int code;
    public CompanyException(CompanyErrorStatus errorStatus) {
        this.message = errorStatus.getMessage();
        this.code = errorStatus.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
