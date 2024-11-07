package com.common.config.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventsConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventInitializer() {
        return () -> Events.setPublisher(applicationContext);
    }
}
