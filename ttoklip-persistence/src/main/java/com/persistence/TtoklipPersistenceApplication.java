package com.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.domain"
})
@SpringBootApplication
public class TtoklipPersistenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtoklipPersistenceApplication.class, args);
    }

}
