package org.bcbs.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api-gateway")
public class GatewayProperties {

    private boolean dataCrypto;

    // Getter & setter.
    public boolean getDataCrypto() {
        return dataCrypto;
    }

    public void setDataCrypto(boolean dataCrypto) {
        this.dataCrypto = dataCrypto;
    }
}
