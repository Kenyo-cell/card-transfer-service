package org.example.service;

import org.example.exception.IncorrectInputException;
import org.example.repository.MoneyTransferRepository;
import org.example.request.confirm.ConfirmData;
import org.example.request.transfer.TransferData;
import org.example.response.success.SuccessResponse;
import org.example.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {
    @Autowired
    private MoneyTransferRepository repository;

    @Autowired
    private CodeGenerator generator;

    public SuccessResponse transfer(TransferData data) throws IncorrectInputException {
        repository.writeTransferTransaction(data, generator.generate());
        return new SuccessResponse("123");
    }

    public SuccessResponse confirm(ConfirmData data) throws IncorrectInputException {
        return new SuccessResponse("213");
    }
}
