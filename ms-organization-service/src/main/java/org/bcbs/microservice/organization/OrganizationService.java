package org.bcbs.microservice.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"org.bcbs.microservice.organization.dal.model"})
@EnableJpaRepositories(basePackages = {"org.bcbs.microservice.organization.dal.repository"})
@ComponentScan(basePackages = {"org.bcbs.microservice"})
public class OrganizationService {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationService.class, args);
    }
}
