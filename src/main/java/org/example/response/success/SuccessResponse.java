package org.example.response.success;

public record SuccessResponse(String operationId) {
    public String getOperationId() {
        return operationId;
    }
}
