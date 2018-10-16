package org.bcbs.systemcore.lib.auth.common.constraint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RevokeType {
    ACCESS_TOKEN, USER_TOKENS;

    @JsonCreator
    public static RevokeType fromValue(String value) {
        return RevokeType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }
}
