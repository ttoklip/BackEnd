package com.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.domain",
        "com.persistence"
})
public class ComponentScanConfig {
}
