package org.bcbs.gateway.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableZuulProxy
@ComponentScan(basePackages = {"org.bcbs.gateway"})
public class MobileGateway {

    public static void main(String[] args) {
        SpringApplication.run(MobileGateway.class, args);
    }
}