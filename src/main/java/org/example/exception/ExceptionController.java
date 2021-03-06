package org.example.exception;

import org.example.entity.response.error.ErrorResponse;
import org.example.util.generator.OperationIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @Autowired
    private OperationIdGenerator operationIdGenerator;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBadGatewayException(RuntimeException e) {
        int id = Integer.parseInt(operationIdGenerator.generateId());
        logException(e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), id), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectInputException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException e) {
        int id = Integer.parseInt(operationIdGenerator.generateId());
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
        return new ResponseEntity<>(
                new ErrorResponse(message, Integer.parseInt(operationIdGenerator.generateId())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public void logException(RuntimeException e) {
        System.out.printf("Error %s%n", e.getMessage());
    }
}
