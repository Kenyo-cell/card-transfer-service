package org.example.service;

import org.example.exception.ConfirmationException;
import org.example.exception.IncorrectInputException;
import org.example.exception.TransferException;
import org.example.repository.MoneyTransferRepository;
import org.example.request.confirm.ConfirmData;
import org.example.request.transfer.TransferData;
import org.example.response.success.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {
    @Autowired
    MoneyTransferRepository repository;

    public SuccessResponse transfer(TransferData data) throws IncorrectInputException, TransferException {
        repository.writeTransferTransaction();
        return new SuccessResponse("213");
    }

    public SuccessResponse confirm(ConfirmData data) throws IncorrectInputException, ConfirmationException {
        repository.writeTransferTransaction();
        return new SuccessResponse("213");
    }
}
