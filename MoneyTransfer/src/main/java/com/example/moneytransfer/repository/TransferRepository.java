package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.Card;
import com.example.moneytransfer.model.Forms.TransferForm;
import com.example.moneytransfer.model.Operation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TransferRepository {
    private final Map<String, String> transactionStorage = new HashMap<>();

    public Operation createOperation(TransferForm transferForm) {
        return new Operation(
                new Card(transferForm.getCardFromNumber(),
                        transferForm.getCardFromValidTill(),
                        transferForm.getCardFromCVV()),
                new Card(transferForm.getCardToNumber(), null, null),
                transferForm.getAmount()
        );
    }

    public void saveTransactionKey(String id, String msg) {
        transactionStorage.put(id, msg);
    }

    public boolean isValidKey(String id, String msg) {
        return transactionStorage.get(id).equals(msg);
    }
}
