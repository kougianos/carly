package com.carly.processor.service;

import com.carly.processor.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@Service
@Slf4j
public class FinalizeTransactionService {

    @Value("${carly.main.finalizetransaction.url}")
    private String url;
    private final Random r;

    public FinalizeTransactionService() {
        this.r = new Random();
    }

    public void finalizeTransaction(TransactionDTO transactionDTO) {
        WebClient webClient = WebClient.create(url);
        transactionDTO.setIsSuccessful(r.nextBoolean());
        transactionDTO.setBuyerId(null);
        transactionDTO.setSellerId(null);
        transactionDTO.setCarId(null);

        var response = webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(transactionDTO)
                .retrieve().toBodilessEntity().block();

        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            log.info("Successfully finalized transaction {}", transactionDTO.getId());
        }

    }
}
