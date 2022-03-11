package org.example.repository;

import org.example.request.transfer.TransferData;

import java.util.Optional;

public class FrontMoneyTransferRepository extends MoneyTransferRepository {
    private final String VERIFICATION_CODE = "0000";

    @Override
    public void writeTransferTransaction(TransferData data) {

    }

    @Override
    protected String generateCode() {
        return VERIFICATION_CODE;
    }

    @Override
    public Optional<String> getCodeByOperationId(String operationId) {
        return Optional.ofNullable(null);
    }
}
