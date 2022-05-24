package com.carly.service;

import com.carly.model.dto.FinalizeTransactionDTO;
import com.carly.model.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    void createTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> getAllTransactionsForBuyer(String buyerId);

    List<TransactionDTO> getAllTransactionsForCar(String carId);

    void finalizeTransaction(FinalizeTransactionDTO transactionDto);
}
