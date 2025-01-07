package com.spring.edna.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EdnaException extends Exception{

    HttpStatus statusCode;

    public EdnaException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
