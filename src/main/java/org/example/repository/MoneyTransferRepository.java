package org.example.repository;

import org.example.request.transfer.TransferData;
import org.example.util.CodeGenerator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MoneyTransferRepository {
    public Map<String, String> verificationCodeByOperationId;

    public MoneyTransferRepository() {
        verificationCodeByOperationId = new HashMap<>();
    }

    public void writeTransferTransaction(TransferData data, String verificationCode) {

    }

    public Optional<String> getCodeByOperationId(String operationId) {
        return Optional.empty();
    }
}
