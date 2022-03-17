package org.example;

import org.example.entity.request.confirm.ConfirmData;
import org.example.entity.request.transfer.Amount;
import org.example.entity.request.transfer.TransferData;
import org.example.entity.response.error.ErrorResponse;
import org.example.entity.response.success.SuccessResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(SpringExtension.class)
public class FrontendIntegrationTest {
    private final static String FRONT_CONTAINER_NAME = "card-transfer:front";
    private final static int PORT = 5500;
    private final static String URL = "http://localhost:%d";
    private final static HttpHeaders headers = new HttpHeaders();
    private final ObjectMapper mapper = new ObjectMapper();

    private final TransferData correctTransfer = new TransferData(
            "1234567812345678",
            "02/32",
            "145",
            "1234567890123456",
            new Amount(
                    1234,
                    "RUR"
            )
    );

    private final ConfirmData correctConfirm = new ConfirmData(
            "0",
            "0000"
    );

    private final TransferData equalsCardsNumber = new TransferData(
            "1234567812345678",
            "02/32",
            "145",
            "1234567812345678",
            new Amount(
                    1234,
                    "RUR"
            )
    );

    private final TransferData incorrectDate = new TransferData(
            "1234567812345678",
            "02/12",
            "145",
            "1234567812345678",
            new Amount(
                    1234,
                    "RUR"
            )
    );

    private final ConfirmData incorrectOperationId = new ConfirmData(
            "1",
            "0000"
    );

    private final ConfirmData incorrectConfirmationCode = new ConfirmData(
            "0",
            "0123"
    );

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final static GenericContainer<?> frontContainer = new GenericContainer<>(FRONT_CONTAINER_NAME)
            .withExposedPorts(PORT);

    @BeforeAll
    private static void initHeaders() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    private void startUp() {
        frontContainer.start();
    }

    @AfterEach
    private void stopContainer() {
        frontContainer.stop();
    }

    @Test
    public void shouldPassedWithTransferAndConfirmation() throws JsonProcessingException {
        String transferJson = mapper.writeValueAsString(correctTransfer);
        String confirmJson = mapper.writeValueAsString(correctConfirm);

        HttpEntity<?> transferRequest = new HttpEntity<>(transferJson, headers);
        HttpEntity<?> confirmRequest = new HttpEntity<>(confirmJson, headers);

        ResponseEntity<SuccessResponse> transferResponse = restTemplate.postForEntity(
                "%s/transfer".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                transferRequest,
                SuccessResponse.class
        );

        ResponseEntity<SuccessResponse> confirmResponse = restTemplate.postForEntity(
                "%s/confirmOperation".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                confirmRequest,
                SuccessResponse.class
        );

        SuccessResponse successTransferResponse = new SuccessResponse("0");
        SuccessResponse successConfirmResponse = new SuccessResponse("1");

        Assertions.assertNotEquals(successTransferResponse.operationId(), successConfirmResponse.operationId());
        Assertions.assertEquals(successTransferResponse, transferResponse.getBody());
        Assertions.assertEquals(successConfirmResponse, confirmResponse.getBody());
    }

    @Test
    public void shouldReturnErrorResponseWithEqualsCardNumbers() throws JsonProcessingException {
        String transferJson = mapper.writeValueAsString(equalsCardsNumber);

        HttpEntity<?> transferRequest = new HttpEntity<>(transferJson, headers);

        ResponseEntity<ErrorResponse> transferResponse = restTemplate.postForEntity(
                "%s/transfer".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                transferRequest,
                ErrorResponse.class
        );

        Assertions.assertFalse(transferResponse.getBody().message().isBlank());
    }

    @Test
    public void shouldReturnErrorResponseWithInvalidTill() throws JsonProcessingException {
        String transferJson = mapper.writeValueAsString(incorrectDate);

        HttpEntity<?> transferRequest = new HttpEntity<>(transferJson, headers);

        ResponseEntity<ErrorResponse> transferResponse = restTemplate.postForEntity(
                "%s/transfer".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                transferRequest,
                ErrorResponse.class
        );

        Assertions.assertFalse(transferResponse.getBody().message().isBlank());
    }

    @Test
    public void shouldReturnErrorResponseWithIncorrectOperationId() throws JsonProcessingException {
        String transferJson = mapper.writeValueAsString(correctTransfer);
        String confirmJson = mapper.writeValueAsString(incorrectOperationId);

        HttpEntity<?> transferRequest = new HttpEntity<>(transferJson, headers);
        HttpEntity<?> confirmRequest = new HttpEntity<>(confirmJson, headers);

        ResponseEntity<SuccessResponse> transferResponse = restTemplate.postForEntity(
                "%s/transfer".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                transferRequest,
                SuccessResponse.class
        );

        ResponseEntity<ErrorResponse> confirmResponse = restTemplate.postForEntity(
                "%s/confirmOperation".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                confirmRequest,
                ErrorResponse.class
        );

        SuccessResponse successTransferResponse = new SuccessResponse("0");

        Assertions.assertEquals(successTransferResponse, transferResponse.getBody());
        Assertions.assertFalse(confirmResponse.getBody().message().isBlank());
    }

    @Test
    public void shouldReturnErrorResponseWithConfirmationCode() throws JsonProcessingException {
        String transferJson = mapper.writeValueAsString(correctTransfer);
        String confirmJson = mapper.writeValueAsString(incorrectConfirmationCode);

        HttpEntity<?> transferRequest = new HttpEntity<>(transferJson, headers);
        HttpEntity<?> confirmRequest = new HttpEntity<>(confirmJson, headers);

        ResponseEntity<SuccessResponse> transferResponse = restTemplate.postForEntity(
                "%s/transfer".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                transferRequest,
                SuccessResponse.class
        );

        ResponseEntity<ErrorResponse> confirmResponse = restTemplate.postForEntity(
                "%s/confirmOperation".formatted(URL.formatted(frontContainer.getMappedPort(PORT))),
                confirmRequest,
                ErrorResponse.class
        );

        SuccessResponse successTransferResponse = new SuccessResponse("0");

        System.out.println(transferResponse.getBody());
        System.out.println(confirmResponse.toString());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, confirmResponse.getStatusCode());
        Assertions.assertEquals(successTransferResponse, transferResponse.getBody());
        Assertions.assertFalse(confirmResponse.getBody().message().isBlank());
    }
}
