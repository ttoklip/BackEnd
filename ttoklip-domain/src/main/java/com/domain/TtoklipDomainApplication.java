package com.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
		"com.common",
})
@SpringBootApplication
public class TtoklipDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(TtoklipDomainApplication.class, args);
	}

}
