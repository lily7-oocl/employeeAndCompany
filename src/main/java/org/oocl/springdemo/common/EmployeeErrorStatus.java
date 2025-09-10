package org.oocl.springdemo.common;

import org.springframework.http.HttpStatus;

public enum EmployeeErrorStatus {
    EMPLOYEE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "not found"),
    EMPLOYEE_NOT_IN_AMONG_AGE(HttpStatus.BAD_REQUEST.value(),"not in among age");

    private final int code;
    private final String message;

    EmployeeErrorStatus(int value, String message) {
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
