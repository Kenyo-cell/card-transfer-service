package org.example;

import org.example.controller.MoneyTransferController;
import org.example.exception.ConfirmationException;
import org.example.exception.IncorrectInputException;
import org.example.request.confirm.ConfirmData;
import org.example.request.transfer.TransferData;
import org.example.response.error.ErrorResponse;
import org.example.response.success.SuccessResponse;
import org.example.service.MoneyTransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Test
    public void correctJsonDataProvidesOkCodeAndJsonMediaTypeAndCorrectContent() throws Exception {
        String testOperationId = "0";
        String json = "{\"cardFromNumber\": \"0000000000000000\",\"cardFromValidTill\":\"12/24\",\"cardFromCVV\":\"123\",\"cardToNumber\":\"1234567890123456\",\"amount\":{\"value\":100,\"currency\":\"RUR\"}}";
        TransferData data = Jackson2ObjectMapperBuilder.json().build()
                .readValue(json.getBytes(), TransferData.class);

        Mockito.when(service.transfer(data)).thenReturn(new SuccessResponse(testOperationId));

        var result = mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = Jackson2ObjectMapperBuilder.json().build()
                .readValue(result.getResponse().getContentAsByteArray(), SuccessResponse.class);
        Assertions.assertEquals(testOperationId, response.operationId());
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectTransferData() throws Exception {
        String message = "Card From Number and Card To Number should have different values";
        String json = "{\"cardFromNumber\": \"0000000000000000\",\"cardFromValidTill\":\"12/24\",\"cardFromCVV\":\"123\",\"cardToNumber\":\"0000000000000000\",\"amount\":{\"value\":100,\"currency\":\"RUR\"}}";
        TransferData data = Jackson2ObjectMapperBuilder.json().build()
                        .readValue(json.getBytes(), TransferData.class);

        Mockito.when(service.transfer(data)).thenThrow(new IncorrectInputException(message));

        var result = mockMvc.perform(
                post("/transfer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        var response = Jackson2ObjectMapperBuilder.json().build()
                .readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);
        Assertions.assertEquals(response.message(), message);
    }

    @Test
    public void correctConfirmOperationReturnsOkAndCorrectContent() throws Exception {
        String operationId = "12345679";
        String json = "{\"operationId\":\"12345678\",\"code\":\"0000\"}";
        ConfirmData data = Jackson2ObjectMapperBuilder.json().build()
                .readValue(json, ConfirmData.class);

        Mockito.when(service.confirm(data)).thenReturn(new SuccessResponse(operationId));

        var result = mockMvc.perform(
                post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = Jackson2ObjectMapperBuilder.json().build()
                        .readValue(result.getResponse().getContentAsByteArray(), SuccessResponse.class);

        Assertions.assertEquals(operationId, response.operationId());
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectConfirmData() throws Exception {
        String exceptionMessage = "Invalid Confirmation Code";
        String json = "{\"operationId\":\"12345678\",\"code\":\"0000\"}";
        ConfirmData data = Jackson2ObjectMapperBuilder.json().build()
                .readValue(json, ConfirmData.class);

        Mockito.when(service.confirm(data)).thenThrow(new ConfirmationException(exceptionMessage));

        var result = mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadGateway())
                .andReturn();

        var response = Jackson2ObjectMapperBuilder.json().build()
                .readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        Assertions.assertEquals(exceptionMessage, response.message());
    }
}
