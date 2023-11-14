package com.example.moneytransfer.model;

public class Amount {
    private int value;
    private String currency;

    public Amount() {
    }

    public Amount(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

}
