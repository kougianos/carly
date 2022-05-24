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
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO findById(String userId) {
        User user = getUser(userId);
        return ConvertUtils.toUserDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(ConvertUtils::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO patchById(String userId, UserDTO userDTO) {
        User user = getUser(userId);

        User updatedUser = ConvertUtils.toUser(userDTO);
        ObjectUtils.merge(user, updatedUser);
        userRepository.save(user);
        log.info("User updated: {}", user);
        return ConvertUtils.toUserDTO(user);
    }

    @Override
    public UserDTO deleteById(String userId) {
        User user = getUser(userId);

        userRepository.deleteById(userId);
        log.info("User deleted: {}", user);
        return ConvertUtils.toUserDTO(user);
    }

    @Override
    public void addCar(String userId, String carId) {
        User user = getUser(userId);
        if (user.getOwnedCars().contains(carId)) {
            throw new IllegalArgumentException(String.format("User %s already has car %s", userId, carId));
        }
        user.addCar(carId);
        userRepository.save(user);
        log.info("Car {} added to user {}", carId, userId);
    }

    @Override
    public void removeCar(String userId, String carId) {
        User user = getUser(userId);
        if (!user.getOwnedCars().contains(carId)) {
            throw new IllegalArgumentException(String.format("User %s does not have car %s", userId, carId));
        }
        user.removeCar(carId);
        userRepository.save(user);
        log.info("Car {} removed from user {}", carId, userId);
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", userId)));
    }

}
