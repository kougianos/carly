package com.carly.controller;

import com.carly.model.dto.FinalizeTransactionDTO;
import com.carly.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final TransactionService transactionService;

    public PublicController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions/finalize")
    public void finalizeTransaction(@RequestBody @Valid FinalizeTransactionDTO transactionDTO) {
        transactionService.finalizeTransaction(transactionDTO);
    }

}
