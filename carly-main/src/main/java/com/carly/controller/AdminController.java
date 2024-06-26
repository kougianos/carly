package com.carly.controller;


import com.carly.model.dto.RegisterRequestDTO;
import com.carly.model.dto.UserDTO;
import com.carly.security.service.AuthorizationService;
import com.carly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public AdminController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public UserDTO getUser(@PathVariable String userId) {
        return userService.findById(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public void createUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        authorizationService.register(registerRequestDTO);
    }

    @PatchMapping("/users/{userId}")
    public UserDTO patchUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        return userService.patchById(userId, userDTO);
    }

    @DeleteMapping("/users/{userId}")
    public UserDTO deleteUser(@PathVariable String userId) {
        return userService.deleteById(userId);
    }

}