package com.walmart.ticketservice.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException(Exception exception, HttpServletRequest request) {
        // Reference id generated will be used in logs and in error sent from the services to the client
        long errorId = ErrorIdGenerator.getNewId();
        logger.error("Database exception, reference Id :" + errorId, exception);
        return createServerError500Response(errorId);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleException(Exception exception, HttpServletRequest request) {

        long errorId = ErrorIdGenerator.getNewId();
        logger.error("Expected entity not found while processing request, please validate input or contact support team  :" + errorId, exception);
        String exceptionInfo = ErrorFormatter.formatErrorMessage(
                "Expected entity not found while processing request, please validate input or please contact support team",
                errorId);
        CustomRestClientResponseException exceptionResponse = new CustomRestClientResponseException(exceptionInfo, HttpStatus.NOT_FOUND.value(), null, null, null, null);

        return new ResponseEntity<Object>(exceptionResponse, new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(Exception exception, HttpServletRequest request) {
        long errorId = ErrorIdGenerator.getNewId();
        logger.error("Validation error  :" + errorId, exception);
        String exceptionInfo = ErrorFormatter.formatErrorMessage(
                "Validation error : " + exception.getMessage(),
                errorId);
        CustomRestClientResponseException exceptionResponse = new CustomRestClientResponseException(exceptionInfo, HttpStatus.BAD_REQUEST.value(), null, null, null, null);

        return new ResponseEntity<Object>(exceptionResponse, new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception exception, HttpServletRequest request) {
        // Reference id generated this will be used in logs and in error sent from the services to the client
        long errorId = ErrorIdGenerator.getNewId();
        logger.error("Unknown exception, reference Id :" + errorId, exception);
        return createServerError500Response(errorId);
    }


    private ResponseEntity<Object> createServerError500Response(long errorId) {
        String exceptionInfo = ErrorFormatter.formatErrorMessage(
                "Internal server error occurred during the processing of the request, please contact support team ",
                errorId);
        CustomRestClientResponseException exceptionResponse = new CustomRestClientResponseException(exceptionInfo, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, null, null);

        return new ResponseEntity<Object>(exceptionResponse, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
