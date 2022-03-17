package org.example.repository;

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

    public String getOperationIdWithConfirmedOperationId(String transferOperationId) {
        if (Optional.ofNullable(verificationCodeByOperationId.get(transferOperationId)).isEmpty())
            throw new IncorrectInputException("Unknown operationId");

        verificationCodeByOperationId.remove(transferOperationId);
        return generator.generateId();
    }
}
