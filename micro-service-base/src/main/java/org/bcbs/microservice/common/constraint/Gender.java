package org.bcbs.microservice.common.constraint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE, FEMALE;

    @JsonCreator
    public static Gender fromValue(String value) {
        return Gender.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }
}
