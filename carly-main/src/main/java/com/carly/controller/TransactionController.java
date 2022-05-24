package com.carly.controller;

import com.carly.model.dto.CarDTO;
import com.carly.model.dto.TransactionDTO;
import com.carly.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
@PreAuthorize("hasRole('USER')")
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public void createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		transactionService.createTransaction(transactionDTO);
	}

}
