package com.carly.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String id;
    private String token;
    private String type;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private boolean isVerified;
    private List<String> roles;
}