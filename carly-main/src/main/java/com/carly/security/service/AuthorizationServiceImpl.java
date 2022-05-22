package com.carly.security.service;

import com.carly.enumeration.ERole;
import com.carly.exception.ResourceExistsException;
import com.carly.exception.ResourceNotFoundException;
import com.carly.model.Role;
import com.carly.model.collection.User;
import com.carly.model.dto.LoginRequestDTO;
import com.carly.model.dto.LoginResponseDTO;
import com.carly.model.dto.RegisterRequestDTO;
import com.carly.repository.RoleRepository;
import com.carly.repository.UserRepository;
import com.carly.security.UserDetailsImpl;
import com.carly.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthorizationServiceImpl(UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    PasswordEncoder encoder,
                                    AuthenticationManager authenticationManager,
                                    JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void register(@Valid RegisterRequestDTO registerRequestDTO) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(registerRequestDTO.getUsername()))) {
            throw new ResourceExistsException(
                    String.format("Username %s already exists!", registerRequestDTO.getUsername()));
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(registerRequestDTO.getEmail()))) {
            throw new ResourceExistsException(
                    String.format("Email %s already exists!", registerRequestDTO.getEmail()));
        }
        User user = new User(registerRequestDTO.getUsername(),
                registerRequestDTO.getEmail(),
                encoder.encode(registerRequestDTO.getPassword()),
                registerRequestDTO.getFirstname(),
                registerRequestDTO.getLastname(),
                registerRequestDTO.getTelephone()
        );

        Set<String> strRoles = registerRequestDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_FREE)
                    .orElseThrow(() -> new ResourceNotFoundException("Free Role not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Admin Role not found"));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role enterpriseRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException("User Role not found"));
                        roles.add(enterpriseRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_FREE)
                                .orElseThrow(() -> new ResourceNotFoundException("Free Role not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setUserVerified(false);
        userRepository.save(user);
        log.info("New user registered: {}", user);

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        java.lang.String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return LoginResponseDTO.builder()
                .id(userDetails.getId())
                .token(jwt)
                .type("BEARER")
                .username(userDetails.getUsername())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .telephone(userDetails.getTelephone())
                .isVerified(userDetails.isVerified())
                .roles(roles)
                .build();

    }

}