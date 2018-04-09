package org.bcbs.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hi")
public class TestController {

    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping
    public String hello() {
        return String.format("Hi there, this is '%s' !", this.serviceName);
    }
}
