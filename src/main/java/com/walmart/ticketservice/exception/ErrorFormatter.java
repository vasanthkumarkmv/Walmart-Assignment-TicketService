package com.walmart.ticketservice.exception;

public class ErrorFormatter {

    private ErrorFormatter() {

    }

    public static String formatErrorMessage(String errorMessage, long errorId) {
        return String.format("%s (error reference: %d)", errorMessage, errorId);
    }

}
