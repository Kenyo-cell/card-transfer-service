package org.example.controller;

import org.example.request.confirm.ConfirmData;
import org.example.request.transfer.TransferData;
import org.example.response.success.SuccessResponse;
import org.example.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class MoneyTransferController {
    @Autowired
    private MoneyTransferService service;

    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> transfer(@RequestBody TransferData transferData) {
        return ResponseEntity.ok(service.transfer());
    }

    @PostMapping(value = "/confirmOperation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> confirmOperation(@RequestBody ConfirmData confirmData) {
        return ResponseEntity.ok(service.confirm());
    }
}
