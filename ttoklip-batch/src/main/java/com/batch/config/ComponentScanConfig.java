package com.batch.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("batchComponentScanConfig")
@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.domain",
        "com.batch"
})
public class ComponentScanConfig {
}
