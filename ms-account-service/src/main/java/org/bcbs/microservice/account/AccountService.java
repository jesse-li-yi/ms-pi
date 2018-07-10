package org.bcbs.microservice.account;

import org.bcbs.systemcore.lib.auth.annotation.EnableAuthServer;
import org.bcbs.systemcore.lib.auth.annotation.EnableResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"org.bcbs.microservice.account.dal.model"})
@EnableJpaRepositories(basePackages = {"org.bcbs.microservice.account.dal.repository"})
@ComponentScan(basePackages = {"org.bcbs.microservice"})
@EnableAuthServer
@EnableResourceServer
public class AccountService {

    public static void main(String[] args) {
        SpringApplication.run(AccountService.class, args);
    }
}
