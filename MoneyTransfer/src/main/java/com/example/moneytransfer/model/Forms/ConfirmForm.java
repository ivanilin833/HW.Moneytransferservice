package com.example.moneytransfer.model.Forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ConfirmForm implements InputForm {
    @NotBlank
    @Size(min = 16)
    private final String operationId;
    @NotBlank
    @Size(min = 4)
    private final String code;

    public ConfirmForm(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }
}
