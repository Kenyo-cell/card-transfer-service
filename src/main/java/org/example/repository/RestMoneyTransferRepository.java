package org.example.repository;

import org.example.request.transfer.TransferData;

import java.util.Optional;

public class RestMoneyTransferRepository extends MoneyTransferRepository {

    @Override
    public void writeTransferTransaction(TransferData data) {

    }

    @Override
    protected String generateCode() {
        return null;
    }

    @Override
    public Optional<String> getCodeByOperationId(String operationId) {
        return Optional.empty();
    }
}
