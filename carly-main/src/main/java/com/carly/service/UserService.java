package com.carly.service;

import com.carly.exception.ResourceNotFoundException;
import com.carly.model.collection.User;
import com.carly.model.dto.UserDTO;
import com.carly.repository.UserRepository;
import com.carly.util.ConvertUtils;
import com.carly.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements CrudService<UserDTO> {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", id)));
        return ConvertUtils.toUserDto(user);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(ConvertUtils::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO patchById(String id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", id)));

        User updatedUser = ConvertUtils.toUser(userDTO);
        ObjectUtils.merge(user, updatedUser);
        userRepository.save(user);
        log.info("User updated: {}", user);
        return ConvertUtils.toUserDto(user);
    }

    @Override
    public UserDTO deleteById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", id)));

        userRepository.deleteById(id);
        log.info("User deleted: {}", user);
        return ConvertUtils.toUserDto(user);
    }

}
