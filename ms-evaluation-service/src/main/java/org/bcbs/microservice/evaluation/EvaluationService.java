package org.bcbs.microservice.evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"org.bcbs.microservice.evaluation.dal.model"})
@EnableJpaRepositories(basePackages = {"org.bcbs.microservice.evaluation.dal.repository"})
@ComponentScan(basePackages = {"org.bcbs.microservice"})
public class EvaluationService {

    public static void main(String[] args) {
        SpringApplication.run(EvaluationService.class, args);
    }
}
