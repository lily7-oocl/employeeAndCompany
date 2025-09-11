package org.oocl.springdemo.common;

import org.springframework.http.HttpStatus;

public enum CompanyErrorStatus {
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "not found");

    private final int code;
    private final String message;

    CompanyErrorStatus(int value, String message) {
        this.code = value;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
