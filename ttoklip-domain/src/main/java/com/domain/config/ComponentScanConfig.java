package com.domain.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("domainComponentScanConfig")
@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.domain",
})
public class ComponentScanConfig {
}
