package com.carly.controller;

import com.carly.model.dto.LoginRequestDTO;
import com.carly.model.dto.LoginResponseDTO;
import com.carly.model.dto.RegisterRequestDTO;
import com.carly.security.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        authorizationService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authorizationService.login(loginRequestDTO);
    }

}