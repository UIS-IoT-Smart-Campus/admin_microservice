package com.iot.admin.admin.errors;

import java.util.HashMap;
import java.util.Map;

//import com.edwin.iot.userservice.utils.Str;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


public class Errors {
    /**
     * Defines the JSON property name that holds all errors.
     */
    public static final String ERROR_KEY_NAME = "errors";

    /**
     * Creates a map with all validation errors thrown from
     * {@link MethodArgumentNotValidException} exception.
     * 
     * @param ex instance with all errors specified by Spring Framework and Java
     *           Validation API.
     * @return {@link Map}
     */
    public static Map<String, Object> fromValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String key = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            //key = Str.camelToSnake(key);
            errors.put(key, message);
        });
        return build(errors);
    }

    /**
     * Creates a map with a validation error for a field thrown from
     * {@link FieldException} exception.
     * 
     * @param ex {@link FieldException} that contains the error data.
     * @return {@link Map}
    */     

    public static Map<String, Object> fromFieldException(FieldException ex) {
        Map<String, String> error = new HashMap<>();
        //String key = Str.camelToSnake(ex.getKey());
        error.put(ex.getField(), ex.getMessage());
        return build(error);
    }
    

    /**
     * Creates a map that holds all validations errors given.
     * 
     * @param errors validation errors.
     * @return {@link Map}
     */
    public static Map<String, Object> build(Map<String, String> errors) {
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR_KEY_NAME, errors);
        return map;
    }
}
