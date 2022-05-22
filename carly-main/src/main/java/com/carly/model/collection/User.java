package com.carly.model.collection;

import com.carly.model.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"password", "id"})
@Document(collection = "users")
@Builder
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;
    private String firstName;
    private String lastName;
    private String telephone;
    private Set<Role> roles = new HashSet<>();
    @NotBlank
    private Set<Api> apis = new HashSet<>();

    private boolean isUserVerified;

    public User(String username, String email, String password, String firstName, String lastName, String telephone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
    }
}