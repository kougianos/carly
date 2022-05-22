package com.carly.model.dto;

import com.carly.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
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

}