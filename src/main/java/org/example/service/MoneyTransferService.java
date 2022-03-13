package org.example.service;

import org.example.exception.IncorrectInputException;
import org.example.repository.MoneyTransferRepository;
import org.example.request.confirm.ConfirmData;
import org.example.request.transfer.TransferData;
import org.example.response.success.SuccessResponse;
import org.example.util.generator.CodeGenerator;
import org.example.util.validate.CardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {
    @Autowired
    private MoneyTransferRepository repository;

    @Autowired
    private CodeGenerator generator;

    public SuccessResponse transfer(TransferData data) throws IncorrectInputException {
        new CardValidator().validate(data);

        String operationId = repository.writeTransferTransactionAndGetOperationId(data, generator.generate());

        if (operationId.isBlank())
            throw new RuntimeException("Can't write transaction");

        return new SuccessResponse(operationId);
    }

    public SuccessResponse confirm(ConfirmData data) throws IncorrectInputException {
        String operationId = repository.getCodeByOperationId(data.getOperationId())
                .orElseThrow(() -> new IncorrectInputException("Can't find presented Operation Id"));
        return new SuccessResponse(operationId);
    }
}
