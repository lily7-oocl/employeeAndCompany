package org.oocl.springdemo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeException.class)
    public String handleEmployeeException(EmployeeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CompanyException.class)
    public String handleCompanyException(CompanyException e) {
        return e.getMessage();
    }
}
