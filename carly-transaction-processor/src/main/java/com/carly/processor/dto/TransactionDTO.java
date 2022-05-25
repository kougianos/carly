package com.carly.processor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private String id;
    private String buyerId;
    private String sellerId;
    private String carId;
    private Boolean isSuccessful;
}
