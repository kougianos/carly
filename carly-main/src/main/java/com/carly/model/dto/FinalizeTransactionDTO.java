package com.carly.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FinalizeTransactionDTO {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    private Boolean isSuccessful;

}
