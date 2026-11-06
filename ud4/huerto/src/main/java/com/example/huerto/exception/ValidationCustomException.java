package com.example.huerto.exception;

public class ValidationCustomException extends RuntimeException{

    public ValidationCustomException(String message) {
        super(message);
    }
}
