package org.example.repository;

import org.example.request.transfer.TransferData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MoneyTransferRepository {
    private Map<String, String> verificationCodeByOperationId;

    public MoneyTransferRepository() {
        verificationCodeByOperationId = new HashMap<>();
    }

    public String writeTransferTransactionAndGetOperationId(TransferData data, String verificationCode) {
        return "";
    }

    public Optional<String> getCodeByOperationId(String operationId) {
        return Optional.empty();
    }
}
