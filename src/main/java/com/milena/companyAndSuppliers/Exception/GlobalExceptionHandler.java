package com.milena.companyAndSuppliers.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({AppException.class})
    public ResponseEntity<Object> handleAppException(final AppException ex){
        final HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("message", ex.getMessage());

        System.out.println(ex.getMessage());
        return new ResponseEntity<>(returnObject, ex.getStatusCode());
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternal(final  RuntimeException ex){
        final HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("message", "internal Server Error");

        System.out.println(ex.getMessage());
        return new ResponseEntity<>(returnObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
