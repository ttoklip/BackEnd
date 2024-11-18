package com.notification.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("notificationComponentScanConfig")
@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.notification"
})
public class ComponentScanConfig {
}
