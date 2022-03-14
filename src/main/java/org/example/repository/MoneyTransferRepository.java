package org.example.repository;

import org.example.request.transfer.TransferData;
import org.example.util.generator.OperationIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MoneyTransferRepository {
    private Map<String, String> verificationCodeByOperationId;

    @Autowired
    private OperationIdGenerator generator;

    public MoneyTransferRepository() {
        verificationCodeByOperationId = new HashMap<>();
    }

    public String writeTransferTransactionAndGetOperationId(TransferData data, String verificationCode) {

        return generator.generateId();
    }

    public Optional<String> getCodeByOperationId(String operationId) {
        return Optional.ofNullable(verificationCodeByOperationId.get(operationId));
    }
}
