package org.bcbs.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api-gateway", ignoreInvalidFields = true)
public class GatewayConfiguration {

    public boolean dataCrypto;
}
