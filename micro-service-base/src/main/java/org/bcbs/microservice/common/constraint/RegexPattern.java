package org.bcbs.microservice.common.constraint;

public final class RegexPattern {

    public static final String PHONE_NO = "^\\d{11}$";
    public static final String STRONG_CIPHER = "^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\\d!@#$%^&*]+$)[a-zA-Z]([a-zA-Z\\d!@#$%^&*]{11,31})$";

    private RegexPattern() {
    }
}
