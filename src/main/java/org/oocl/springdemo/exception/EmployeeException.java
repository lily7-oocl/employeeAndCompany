package org.oocl.springdemo.exception;

import org.oocl.springdemo.common.EmployeeErrorStatus;

public class EmployeeException extends RuntimeException {
    private final String message;
    private final int code;
    public EmployeeException(EmployeeErrorStatus errorStatus) {
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
