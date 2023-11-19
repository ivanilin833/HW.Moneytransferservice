package com.example.moneytransfer;

import com.example.moneytransfer.exceptions.ConfirmationFail;
import com.example.moneytransfer.exceptions.ServerError;
import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.Card;
import com.example.moneytransfer.model.Forms.ConfirmForm;
import com.example.moneytransfer.model.Forms.TransferForm;
import com.example.moneytransfer.model.Operation;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.services.TransferService;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransferServiceTest {

    @Mock
    private Validator validator;

    @Mock
    private TransferRepository repository;
    private TransferService transferService;
    private TransferForm transferForm;
    private ConfirmForm confirmForm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService(validator, repository);
        transferForm = new TransferForm("2222333344445555", "11/29", "222",
                "5555444433332222", new Amount(500000, "RUB"));
        confirmForm = new ConfirmForm(UUID.randomUUID().toString(), "0000");
    }

    @Test
    void testTransferSuccess() {
        // Arrange
        when(validator.validate(transferForm)).thenReturn(Collections.emptySet());
        when(repository.createOperation(transferForm)).thenReturn(new Operation(new Card("2222333344445555", "11/29", "222"),
                new Card("5555444433332222", null, null), new Amount(500000, "RUB")));

        // Act
        String result = transferService.transfer(transferForm);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testTransferServerError() {
        // Arrange
        when(validator.validate(transferForm)).thenReturn(Collections.emptySet());
        when(repository.createOperation(transferForm)).thenThrow(new RuntimeException("Test Exception"));

        // Act and Assert
        assertThrows(ServerError.class, () -> transferService.transfer(transferForm));
    }

    @Test
    void testConfirmSuccess() {
        when(validator.validate(confirmForm)).thenReturn(Collections.emptySet());
        when(repository.isValidKey(confirmForm.getOperationId(), confirmForm.getCode())).thenReturn(true);
        // Act
        String result = transferService.confirm(confirmForm);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testConfirmInvalidCode() {
        // Arrange
        var invalidConfirmForm = new ConfirmForm(UUID.randomUUID().toString(), "1111");
        when(validator.validate(confirmForm)).thenReturn(Collections.emptySet());

        // Act and Assert
        assertThrows(ConfirmationFail.class, () -> transferService.confirm(invalidConfirmForm));
    }

    @Test
    void testIdentityOperationId() {
        // Arrange
        when(validator.validate(confirmForm)).thenReturn(Collections.emptySet());
        when(repository.createOperation(transferForm)).thenReturn(new Operation(new Card("2222333344445555", "11/29", "222"),
                new Card("5555444433332222", null, null), new Amount(500000, "RUB")));

        // Act
        var transferId = transferService.transfer(transferForm);
        when(repository.isValidKey(transferId, "0000")).thenReturn(true);
        var testIdConfirmFom = new ConfirmForm(transferId, "0000");

        //Assert
        assertEquals(transferId, transferService.confirm(testIdConfirmFom));
    }
}
