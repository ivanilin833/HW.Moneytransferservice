package com.example.moneytransfer.controller;


import com.example.moneytransfer.model.Forms.ConfirmForm;
import com.example.moneytransfer.model.Forms.TransferForm;
import com.example.moneytransfer.model.okResponse;
import com.example.moneytransfer.services.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {
    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @CrossOrigin
    @PostMapping("/transfer")
    @ResponseBody
    public okResponse transferMoney(@RequestBody TransferForm transferForm) {
        logger.info("CardFrom: " + transferForm.getCardFromNumber() + " cardTo: " + transferForm.getCardToNumber()
                + " amount: " + transferForm.getAmount().getValue() + " commision: " + transferForm.getAmount().getValue() / 10000);
        return new okResponse(transferService.transfer(transferForm));
    }

    @CrossOrigin
    @PostMapping("/confirmOperation")
    public okResponse confirmOperation(@RequestBody ConfirmForm confirmForm) {
        logger.info("operationId: " + confirmForm.getOperationId() + " code: " + confirmForm.getCode());
        return new okResponse(transferService.confirm(confirmForm));
    }
}
