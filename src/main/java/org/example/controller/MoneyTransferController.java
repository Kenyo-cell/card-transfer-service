package org.example.controller;

import org.example.entity.request.confirm.ConfirmData;
import org.example.entity.request.transfer.TransferData;
import org.example.entity.response.success.SuccessResponse;
import org.example.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
@Validated
public class MoneyTransferController {
    @Autowired
    private MoneyTransferService service;

    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> transfer(@RequestBody @Valid TransferData transferData) {
        return ResponseEntity.ok(service.transfer(transferData));
    }

    @PostMapping(value = "/confirmOperation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> confirmOperation(@RequestBody @Valid ConfirmData confirmData) {
        return ResponseEntity.ok(service.confirm(confirmData));
    }
}
