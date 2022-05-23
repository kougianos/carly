package com.carly.service;

import com.carly.model.collection.User;
import com.carly.model.dto.UserDTO;

import java.util.List;

public interface UserService {
	UserDTO findById(String userId);

	List<UserDTO> findAll();

	UserDTO patchById(String userId, UserDTO userDTO);

	UserDTO deleteById(String userId);

	User getUser(String userId);

	void addCar(String userId, String carId);

	void removeCar(String userId, String carId);
}
