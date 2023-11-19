package com.example.moneytransfer.services;

import com.example.moneytransfer.exceptions.ConfirmationFail;
import com.example.moneytransfer.exceptions.InvalidDataException;
import com.example.moneytransfer.exceptions.ServerError;
import com.example.moneytransfer.model.Forms.ConfirmForm;
import com.example.moneytransfer.model.Forms.InputForm;
import com.example.moneytransfer.model.Forms.TransferForm;
import com.example.moneytransfer.repository.TransferRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private final Validator validator;
    private final TransferRepository repository;

    public TransferService(Validator validator, TransferRepository repository) {
        this.repository = repository;
        this.validator = validator;
    }

    public String transfer(TransferForm form) {
        String result;
        isValid(form);
        try {
            result = repository.createOperation(form).getId().toString();
            repository.saveTransactionKey(result, getCheckMsg());
        } catch (RuntimeException exception) {
            throw new ServerError("Error transfer " + exception.getMessage());
        }
        logger.info( "CardFrom: " + form.getCardFromNumber() + " cardTo: " + form.getCardToNumber()
                + " amount: " + form.getAmount().getValue() + " commision: " + (float)(form.getAmount().getValue()/10000)
                + " result: transfer success, await confirm, operationId :" + result);
        return result;
    }

    public String confirm(ConfirmForm form) {
        isValid(form);
        try {
            if (!repository.isValidKey(form.getOperationId(), form.getCode())) {
                throw new ConfirmationFail("Invalid operation confirmation code");
            }
        } catch (ConfirmationFail confirmationFail) {
            throw confirmationFail;
        } catch (Exception exception) {
            throw new ServerError("Error confirmation " + exception.getMessage());
        }
        logger.info("Transfer with operationId: " + form.getOperationId() + " is success");
        return form.getOperationId();
    }

    private void isValid(InputForm form) {
        Set<ConstraintViolation<InputForm>> violations = validator.validate(form);
        if (!violations.isEmpty()) {
            throw new InvalidDataException(violations.stream().map(v -> v.getPropertyPath() + ":" + v.getMessage()).collect(Collectors.joining(";")));
        }
    }

    private String getCheckMsg() {
        return "0000";
    }
}
