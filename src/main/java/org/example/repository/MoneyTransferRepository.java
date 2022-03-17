package org.example.repository;

import org.example.entity.request.confirm.ConfirmData;
import org.example.entity.writer.WriteData;
import org.example.exception.IncorrectInputException;
import org.example.entity.request.transfer.TransferData;
import org.example.util.generator.OperationIdGenerator;
import org.example.util.writer.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MoneyTransferRepository {
    private Map<String, String> verificationCodeByOperationId;

    @Autowired
    private OperationIdGenerator generator;

    @Autowired
    private Writer writer;

    public MoneyTransferRepository() {
        verificationCodeByOperationId = new ConcurrentHashMap<>();
    }

    public String writeTransferTransactionAndGetOperationId(TransferData data, String verificationCode) {
        String operationId = generator.generateId();
        writer.write(WriteData.from(data));
        verificationCodeByOperationId.put(operationId, verificationCode);
        return operationId;
    }

    public String getOperationIdWithConfirmedTransaction(ConfirmData data) {
        if (!verificationCodeByOperationId.containsKey(data.getOperationId()))
            throw new IncorrectInputException("Unknown operationId");

        if (!verificationCodeByOperationId.get(data.getOperationId()).equals(data.getCode()))
            throw new IncorrectInputException("Confirmation Exception: incorrect verification code");

        verificationCodeByOperationId.remove(data.getOperationId());
        return generator.generateId();
    }
}
