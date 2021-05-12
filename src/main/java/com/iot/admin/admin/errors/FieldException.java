package com.iot.admin.admin.errors;

import lombok.Getter;

public class FieldException  extends RuntimeException{

    /**
     * Field name that thrown the exception.
    */
    @Getter
    private String field;

    public FieldException(String field, String message){
        super(message);
        this.field = field;
    }

}
