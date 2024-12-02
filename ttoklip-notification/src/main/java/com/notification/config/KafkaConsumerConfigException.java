package com.notification.config;

import com.notification.consumer.ErrorMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

@Configuration
public class KafkaConsumerConfigException {

    private final KafkaPropertiesConfigException kafkaPropertiesConfigException;

    public KafkaConsumerConfigException(KafkaPropertiesConfigException kafkaPropertiesConfigException) {
        this.kafkaPropertiesConfigException = kafkaPropertiesConfigException;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ErrorMessage> kafkaListenerContainerFactoryException() {
        ConcurrentKafkaListenerContainerFactory<String, ErrorMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
                kafkaPropertiesConfigException.consumerConfigException(),
                new StringDeserializer(),
                new JsonDeserializer<>(ErrorMessage.class)
        ));

        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
}
