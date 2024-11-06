package com.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
		"com.common",
})
@SpringBootApplication
public class TtoklipInfraApplication {

	public static void main(String[] args) {
		SpringApplication.run(TtoklipInfraApplication.class, args);
	}

}
