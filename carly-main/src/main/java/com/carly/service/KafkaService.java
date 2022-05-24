package com.carly.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${cloudkarafka.topic}")
    private String topic;

    @Autowired
    KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, String message) {
        log.info("Sending kafka message with key {} value {}", key, message);
        this.kafkaTemplate.send(topic, key, message);
    }
}