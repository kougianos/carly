package com.carly.model.dto;

import com.carly.model.collection.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    @NotNull
    @NotEmpty
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private boolean isVerified;
    private Set<Role> roles;
    private Set<String> ownedCars;
}