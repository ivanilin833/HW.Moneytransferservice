package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.Card;
import com.example.moneytransfer.model.Operation;
import com.example.moneytransfer.model.Forms.TransferForm;
import org.springframework.stereotype.Repository;

@Repository
public class TransferRepository {
    public Operation createOperation(TransferForm transferForm) {
        return new Operation(
                new Card(transferForm.getCardFromNumber(),
                        transferForm.getCardFromValidTill(),
                        transferForm.getCardFromCVV()),
                new Card(transferForm.getCardToNumber(), null, null),
                transferForm.getAmount()
        );
    }
}
