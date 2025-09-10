package org.oocl.springdemo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Map<String,String>> handleEmployeeException(EmployeeException e) {
        return ResponseEntity.status(e.getCode()).body(Map.of("errorMessage",e.getMessage()));
    }

    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<Map<String,String>> handleCompanyException(CompanyException e) {
        return ResponseEntity.status(e.getCode()).body(Map.of("errorMessage",e.getMessage()));
    }
}
