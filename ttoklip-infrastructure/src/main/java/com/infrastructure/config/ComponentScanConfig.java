package com.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("infraComponentScanConfig")
@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
})
public class ComponentScanConfig {
}
