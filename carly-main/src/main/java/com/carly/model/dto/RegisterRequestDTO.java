package com.carly.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegisterRequestDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastname;
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String telephone;
    private Set<String> roles;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}