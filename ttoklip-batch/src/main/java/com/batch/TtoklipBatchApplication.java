package com.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.domain",
        "com.persistence"
})
@SpringBootApplication
public class TtoklipBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtoklipBatchApplication.class, args);
    }

}
