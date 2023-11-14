package com.example.moneytransfer.exceptions;

public class InvalidDataException extends RuntimeException {
    private static int id = 0;
    public InvalidDataException(String message) {
        super(message);
        id++;
    }

    public int getId() {
        return id;
    }
}
