package com.example.moneytransfer.model.Forms;

import com.example.moneytransfer.model.Amount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransferForm implements InputForm {
    @NotBlank
    @Size(min = 16)
    private String cardFromNumber;
    @NotBlank
    private String cardFromValidTill;
    @NotBlank
    @Size(min = 3)
    private String cardFromCVV;
    @NotBlank
    @Size(min = 16)
    private String cardToNumber;
    @NotNull
    private Amount amount;

    public TransferForm() {
    }

    public TransferForm(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }
}
