package com.integrador.grupoA.exceptions.custom;


public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}