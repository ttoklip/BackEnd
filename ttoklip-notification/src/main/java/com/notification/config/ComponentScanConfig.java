package com.notification.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.notification"
})
@Configuration
public class ComponentScanConfig {
}
