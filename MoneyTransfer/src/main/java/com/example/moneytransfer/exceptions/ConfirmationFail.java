package com.example.moneytransfer.exceptions;

public class ConfirmationFail extends RuntimeException{
    private static int id = 0;
    public ConfirmationFail(String message) {
        super(message);
        id++;
    }

    public int getId() {
        return id;
    }
}
