package com.carly.processor.service;

import com.carly.processor.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @Value("${cloudkarafka.topic}")
    private String topic;

    @Autowired
    private FinalizeTransactionService finalizeTransactionService;

    @SneakyThrows
    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("Consume message from topic {} offset {} key {}: {}",
                consumerRecord.topic(), consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());

        TransactionDTO transactionDTO = new ObjectMapper().readValue(consumerRecord.value(), TransactionDTO.class);
        finalizeTransactionService.finalizeTransaction(transactionDTO);

    }

}