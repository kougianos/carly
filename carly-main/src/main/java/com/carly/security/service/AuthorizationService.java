package com.carly.security.service;

import com.carly.model.dto.LoginRequestDTO;
import com.carly.model.dto.LoginResponseDTO;
import com.carly.model.dto.RegisterRequestDTO;

import javax.validation.Valid;

public interface AuthorizationService {

    void register(@Valid RegisterRequestDTO registerRequestDTO);

    LoginResponseDTO login(@Valid LoginRequestDTO loginRequestDTO);

}