package org.bcbs.systemcore.lib.auth.common.constraint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GrantType {
    CLIENT_CREDENTIALS, PASSWORD, REFRESH_TOKEN;

    @JsonCreator
    public static GrantType fromValue(String value) {
        return GrantType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }
}
