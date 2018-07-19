package org.bcbs.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {GatewayConfiguration.class})
@ConfigurationProperties(prefix = "api-gateway", ignoreInvalidFields = true)
public class GatewayConfiguration {

    private boolean encryptData;

    // Getter & setter.
    public boolean isEncryptData() {
        return encryptData;
    }

    public void setEncryptData(boolean encryptData) {
        this.encryptData = encryptData;
    }
}
