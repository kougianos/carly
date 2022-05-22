package com.carly.controller;


import com.carly.model.dto.RegisterRequestDTO;
import com.carly.model.dto.UserDTO;
import com.carly.security.service.AuthorizationService;
import com.carly.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
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
    public UserDTO patchUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDTO) {
        return userService.patchUserById(userId, userDTO);
    }

    @DeleteMapping("/users/{userId}")
    public UserDTO deleteUser(@PathVariable String userId) {
        return userService.deleteUserById(userId);
    }

}