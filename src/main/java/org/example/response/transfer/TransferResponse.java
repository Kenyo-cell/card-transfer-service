package org.example.response.transfer;

public class TransferResponse {
    private final String operationId;

    public TransferResponse(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }
}
