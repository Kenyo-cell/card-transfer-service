package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.MoneyTransferController;
import org.example.entity.request.transfer.Amount;
import org.example.exception.IncorrectInputException;
import org.example.entity.request.confirm.ConfirmData;
import org.example.entity.request.transfer.TransferData;
import org.example.entity.response.error.ErrorResponse;
import org.example.entity.response.success.SuccessResponse;
import org.example.service.MoneyTransferService;
import org.example.util.generator.OperationIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MoneyTransferController.class)
public class MoneyTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoneyTransferService service;

    @SpyBean
    private OperationIdGenerator generator;

    private final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    private final TransferData correctTransferData = new TransferData(
            "1234567812345678",
            "02/32",
            "145",
            "1234567890123456",
            new Amount(
                    1234,
                    "RUR"
            )
    );

    private final TransferData equalsCardNumbers = new TransferData(
            "1234567812345678",
            "02/32",
            "145",
            "1234567812345678",
            new Amount(
                    1234,
                    "RUR"
            )
    );

    private final ConfirmData correctConfirm = new ConfirmData(
            "123",
            "0000"
    );

    private final ConfirmData incorrectConfirm = new ConfirmData(
            "133",
            "1234"
    );

    @Test
    public void correctJsonDataProvidesOkCodeAndJsonMediaTypeAndCorrectContent() throws Exception {
        String testOperationId = "0";

        Mockito.when(service.transfer(correctTransferData))
                .thenReturn(new SuccessResponse(testOperationId));

        var result = mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(correctTransferData))
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = mapper.readValue(
                result.getResponse().getContentAsByteArray(),
                SuccessResponse.class
        );

        Assertions.assertEquals(testOperationId, response.operationId());
    }

    @Test
    public void shouldReturnBadRequestWithEqualsCardNumbers() throws Exception {
        Mockito.when(service.transfer(equalsCardNumbers))
                .thenThrow(new IncorrectInputException("Card Numbers should be different"));

        var result = mockMvc.perform(
                post("/transfer")
                        .content(mapper.writeValueAsString(equalsCardNumbers))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        var response = mapper.readValue(
                result.getResponse().getContentAsByteArray(),
                ErrorResponse.class
        );

        Assertions.assertFalse(response.message().isBlank());
    }

    @Test
    public void correctConfirmOperationReturnsOkAndCorrectContent() throws Exception {
        String operationId = "12345679";

        Mockito.when(service.confirm(correctConfirm)).thenReturn(new SuccessResponse(operationId));

        var result = mockMvc.perform(
                post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(correctConfirm))
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = mapper.readValue(
                result.getResponse().getContentAsByteArray(),
                SuccessResponse.class
        );

        Assertions.assertEquals(operationId, response.operationId());
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectConfirmData() throws Exception {
        Mockito.when(service.confirm(incorrectConfirm))
                .thenThrow(new IncorrectInputException("Invalid confirm code"));

        var result = mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(incorrectConfirm))
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        var response = mapper.readValue(
                result.getResponse().getContentAsByteArray(),
                ErrorResponse.class
        );

        Assertions.assertFalse(response.message().isBlank());
    }
}
