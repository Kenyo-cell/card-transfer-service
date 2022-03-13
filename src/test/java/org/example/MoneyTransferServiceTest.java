package org.example;

import org.example.exception.IncorrectInputException;
import org.example.repository.MoneyTransferRepository;
import org.example.request.confirm.ConfirmData;
import org.example.request.transfer.Amount;
import org.example.request.transfer.TransferData;
import org.example.response.success.SuccessResponse;
import org.example.service.MoneyTransferService;
import org.example.util.CodeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MoneyTransferServiceTest {
    @Autowired
    private MoneyTransferService service;

    @Test
    public void shouldThrowExceptionDueToSameCardToAndFromNumbers() {
        String verificationCode = "2123";
        TransferData data = new TransferData(
                "0000000000000000",
                "12/24",
                "123",
                "0000000000000000",
                new Amount(10000, "RUR")
        );

        CodeGenerator generator = Mockito.mock(CodeGenerator.class);
        Mockito.when(generator.generate()).thenReturn(verificationCode);

        MoneyTransferRepository repository = Mockito.mock(MoneyTransferRepository.class);
        Mockito.doNothing().when(repository).writeTransferTransaction(data, verificationCode);

        Assertions.assertThrows(IncorrectInputException.class, () -> service.transfer(data));
    }

    @Test
    public void shouldThrowExceptionDueToCardDateExpired() {
        String verificationCode = "2131";
        TransferData data = new TransferData(
                "0000000000000000",
                "12/20",
                "123",
                "1234567890123456",
                new Amount(10000, "RUR")
        );

        CodeGenerator generator = Mockito.mock(CodeGenerator.class);
        Mockito.when(generator.generate()).thenReturn(verificationCode);

        MoneyTransferRepository repository = Mockito.mock(MoneyTransferRepository.class);
        Mockito.doNothing().when(repository).writeTransferTransaction(data, verificationCode);

        Assertions.assertThrows(IncorrectInputException.class, () -> service.transfer(data));
    }

    @Test
    public void transferShouldReturnSuccessResponse() {
        String verificationCode = "2131";
        TransferData data = new TransferData(
                "0000000000000000",
                "12/24",
                "123",
                "1234567890123456",
                new Amount(10000, "RUR")
        );

        CodeGenerator generator = Mockito.mock(CodeGenerator.class);
        Mockito.when(generator.generate()).thenReturn(verificationCode);

        MoneyTransferRepository repository = Mockito.mock(MoneyTransferRepository.class);
        Mockito.doNothing().when(repository).writeTransferTransaction(data, verificationCode);

        SuccessResponse res = service.transfer(data);
        Assertions.assertFalse(res.operationId().isEmpty());
    }

    @Test
    public void confirmShouldReturnSuccessResponse() {
        String operationId = "123";
        String code = "1000";
        ConfirmData data = new ConfirmData(operationId, code);

        MoneyTransferRepository repository = Mockito.mock(MoneyTransferRepository.class);
        Mockito.when(repository.getCodeByOperationId(data.getOperationId())).thenReturn(Optional.of(code));

        SuccessResponse res = service.confirm(data);
        Assertions.assertFalse(res.operationId().isEmpty());
        Assertions.assertNotEquals(res.operationId(), operationId);
    }
}
