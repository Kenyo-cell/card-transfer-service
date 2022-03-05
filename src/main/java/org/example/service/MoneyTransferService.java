package org.example.service;

import org.example.response.confirm.ConfirmResponse;
import org.example.response.transfer.TransferResponse;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {
    public TransferResponse transfer() {
        return new TransferResponse("");
    }

    public ConfirmResponse confirm() {
        return new ConfirmResponse();
    }
}
