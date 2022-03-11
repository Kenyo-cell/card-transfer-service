package org.example.exception;

import org.example.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


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

    public void logException(RuntimeException e) {
        System.out.println(e.getMessage());
    }
}
