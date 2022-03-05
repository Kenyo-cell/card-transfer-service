package org.example.service;

import org.example.exception.ConfirmationException;
import org.example.exception.IncorrectInputException;
import org.example.exception.TransferException;
import org.example.response.success.SuccessResponse;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {
    public SuccessResponse transfer() throws IncorrectInputException, TransferException {
        return new SuccessResponse("");
    }

    public SuccessResponse confirm() throws IncorrectInputException, ConfirmationException {
        return new SuccessResponse("");
    }
}
