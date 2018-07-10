package org.bcbs.microservice.common.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
        super("No data found");
    }
}
