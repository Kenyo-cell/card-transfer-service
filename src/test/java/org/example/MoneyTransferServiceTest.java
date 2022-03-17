package org.example;

import org.example.exception.IncorrectInputException;
import org.example.repository.MoneyTransferRepository;
import org.example.entity.request.confirm.ConfirmData;
import org.example.entity.request.transfer.Amount;
import org.example.entity.request.transfer.TransferData;
import org.example.entity.response.success.SuccessResponse;
import org.example.service.MoneyTransferService;
import org.example.util.generator.CodeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MoneyTransferServiceTest {
    @Autowired
    private MoneyTransferService service;

    @MockBean
    private CodeGenerator generator;

    @MockBean
    private MoneyTransferRepository repository;

    private final String OPERATION_ID = "1234";
    private final String CODE = "2123";

    @Test
    public void shouldThrowExceptionDueToSameCardToAndFromNumbers() {
        TransferData data = new TransferData(
                "0000000000000000",
                "12/24",
                "123",
                "0000000000000000",
                new Amount(10000, "RUR")
        );

        Mockito.when(generator.generate()).thenReturn(CODE);
        Mockito.when(repository.writeTransferTransactionAndGetOperationId(data, CODE))
                .thenReturn(OPERATION_ID);

        Assertions.assertThrows(IncorrectInputException.class, () -> service.transfer(data));
    }

    @Test
    public void shouldThrowExceptionDueToCardDateExpired() {
        TransferData data = new TransferData(
                "0000000000000000",
                "12/20",
                "123",
                "1234567890123456",
                new Amount(10000, "RUR")
        );

        Mockito.when(generator.generate()).thenReturn(CODE);
        Mockito.when(repository.writeTransferTransactionAndGetOperationId(data, CODE))
                .thenReturn(OPERATION_ID);

        Assertions.assertThrows(IncorrectInputException.class, () -> service.transfer(data));
    }

    @Test
    public void transferShouldReturnSuccessResponse() {
        TransferData data = new TransferData(
                "0000000000000000",
                "12/24",
                "123",
                "1234567890123456",
                new Amount(10000, "RUR")
        );

        Mockito.when(generator.generate()).thenReturn(CODE);
        Mockito.when(repository.writeTransferTransactionAndGetOperationId(data, CODE))
                .thenReturn(OPERATION_ID);

        SuccessResponse res = service.transfer(data);
        Assertions.assertFalse(res.operationId().isEmpty());
    }

    @Test
    public void confirmShouldReturnSuccessResponse() {
        ConfirmData data = new ConfirmData(OPERATION_ID, CODE);

        Mockito.when(repository.getOperationIdWithConfirmedTransaction(data))
                .thenReturn(CODE);

        SuccessResponse res = service.confirm(data);
        Assertions.assertFalse(res.operationId().isEmpty());
        Assertions.assertNotEquals(res.operationId(), OPERATION_ID);
    }
}
