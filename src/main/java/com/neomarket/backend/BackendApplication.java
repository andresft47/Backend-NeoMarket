package com.neomarket.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.neomarket.backend",
    "com.neomarket.controller",
    "com.neomarket.service",
    "com.neomarket.config",
    "com.neomarket.util",
    "com.neomarket.exception"
})
@EntityScan(basePackages = "com.neomarket.model")
@EnableJpaRepositories(basePackages = "com.neomarket.repository")
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}