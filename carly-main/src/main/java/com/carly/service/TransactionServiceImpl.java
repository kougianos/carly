package com.carly.service;

import com.carly.exception.InvalidTransactionException;
import com.carly.exception.ResourceNotFoundException;
import com.carly.model.collection.Car;
import com.carly.model.collection.Transaction;
import com.carly.model.collection.User;
import com.carly.model.dto.FinalizeTransactionDTO;
import com.carly.model.dto.TransactionDTO;
import com.carly.repository.TransactionRepository;
import com.carly.util.ConvertUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CarService carService;
    private final KafkaService kafkaService;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserService userService, CarService carService, KafkaService kafkaService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.carService = carService;
        this.kafkaService = kafkaService;
    }

    @Override
    @SneakyThrows
    public void createTransaction(TransactionDTO transactionDTO) {
        validateTransactionCreation(transactionDTO);
        Transaction transaction = ConvertUtils.toTransaction(transactionDTO);
        transaction.setCreatedAt(LocalDateTime.now());
        String transactionId = transactionRepository.save(transaction).getId();
        log.info("Transaction {} created: {} ", transactionId, transaction);

        transactionDTO.setId(transactionId);
        transactionDTO.setIsSuccessful(null);
        kafkaService.send(transactionId, mapper.writeValueAsString(transactionDTO));
    }

    @Override//TODO
    public List<TransactionDTO> getAllTransactionsForBuyer(String buyerId) {
        List<Transaction> transactions = transactionRepository.findByBuyerId(buyerId);
        return null;
    }

    @Override//TODO
    public List<TransactionDTO> getAllTransactionsForCar(String carId) {
        return null;
    }

    @Override
    public void finalizeTransaction(FinalizeTransactionDTO transactionDto) {
        Transaction transaction = getTransaction(transactionDto.getId());
        if (transaction.getIsSuccessful() != null) {
            throw new IllegalArgumentException(
                    String.format("Transaction %s is already finalized", transaction.getId()));
        }
        transaction.setIsSuccessful(transactionDto.getIsSuccessful());

        if (Boolean.TRUE.equals(transactionDto.getIsSuccessful())) {
            userService.addCar(transaction.getBuyerId(), transaction.getCarId());
            userService.removeCar(transaction.getSellerId(), transaction.getCarId());
            carService.changeCarOwner(transaction.getCarId(), transaction.getBuyerId());
        }
        transactionRepository.save(transaction);
        log.info("Transaction {} finalized to {}", transaction.getId(), transaction.getIsSuccessful());
    }

    public Transaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Transaction %s not found", transactionId)));
    }


    /**
     * Validate
     * - that buyer, seller and car all exist in database.
     * - that seller owns the car being sold.
     *
     * @throws com.carly.exception.ResourceNotFoundException ResourceNotFoundException
     * @throws InvalidTransactionException                   InvalidTransactionException
     */
    private void validateTransactionCreation(TransactionDTO transactionDTO) {
        userService.getUser(transactionDTO.getBuyerId());
        User seller = userService.getUser(transactionDTO.getSellerId());
        Car car = carService.getCar(transactionDTO.getCarId());

        if (!seller.getOwnedCars().contains(transactionDTO.getCarId())) {
            throw new InvalidTransactionException(String.format("Seller %s does not own car %s", seller.getId(),
                    car.getId()));
        }

    }
}
