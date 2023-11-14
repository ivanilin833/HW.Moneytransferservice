package com.example.moneytransfer.exceptions;

public class ServerError extends RuntimeException{
    private static int id = 0;

    public ServerError(String message) {
        super(message);
        id++;
    }

    public int getId() {
        return id;
    }
}
