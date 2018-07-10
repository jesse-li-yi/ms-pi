package org.bcbs.microservice.common.constraint;

public enum Gender {
    MALE("M"), FEMALE("F");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
