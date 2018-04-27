package org.bcbs.microservice.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
        super("No data found");
    }
}
