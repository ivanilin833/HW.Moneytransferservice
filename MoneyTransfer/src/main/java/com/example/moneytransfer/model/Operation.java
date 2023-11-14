package com.example.moneytransfer.model;

import java.util.UUID;

public class Operation {
    private final UUID id = UUID.randomUUID();
    private final Card cardFrom;
    private final Card cardTo;
    private final Amount amount;

    public Operation(Card cardFrom, Card cardTo, Amount amount) {
        this.cardFrom = cardFrom;
        this.cardTo = cardTo;
        this.amount = amount;
    }

    public String getFromNumber(){
        return cardFrom.number();
    }

    public String getFromValidTill(){
        return cardFrom.validTill();
    }

    public String getFromCVV(){
        return cardFrom.cvv();
    }

    public String getToNumber(){
        return cardTo.number();
    }

    public Amount getAmount(){
        return amount;
    }

    public UUID getId() {
        return id;
    }
}
