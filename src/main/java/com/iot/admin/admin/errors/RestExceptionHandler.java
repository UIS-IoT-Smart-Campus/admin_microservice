package com.iot.admin.admin.errors;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {        
        Map<String, Object> errors = Errors.fromValidationException(ex);
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FieldException.class)
    protected ResponseEntity<Object> handleFieldException(FieldException ex, WebRequest request){
        Map<String,Object> errors = Errors.fromFieldException(ex);
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundExeption(EntityNotFoundException ex, WebRequest request){
        Map<String,Object> errors = new HashMap<>();
        errors.put("msg", "Resource not found");
        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

    
}
