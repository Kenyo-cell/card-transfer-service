package org.example.repository;

import org.example.request.transfer.TransferData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public abstract class MoneyTransferRepository {
    protected Map<String, String> verificationCodeByOperationId;

    public MoneyTransferRepository() {
        verificationCodeByOperationId = new HashMap<>();
    }

    public abstract void writeTransferTransaction(TransferData data);
    protected abstract String generateCode();
    public abstract Optional<String> getCodeByOperationId(String operationId);
}
