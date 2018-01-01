package com.walmart.ticketservice.exception;

import java.util.Date;

public class ErrorIdGenerator {

    private ErrorIdGenerator() {

    }

    public static long getNewId() {
        return new Date().getTime();
    }
}