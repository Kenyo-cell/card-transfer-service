package org.example.exception;

import org.example.response.error.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBadGatewayException(RuntimeException e) {
        int id = 0;
        logException(e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), id), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(IncorrectInputException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException e) {
        int id = 0;
        logException(e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), id), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String message = ex.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new ResponseEntity<>(new ErrorResponse(message, 0), HttpStatus.BAD_GATEWAY);
    }

    public void logException(RuntimeException e) {
        System.out.printf("Error %s%n", e.getMessage());
    }
}
