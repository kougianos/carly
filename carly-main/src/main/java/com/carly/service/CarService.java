package com.carly.service;

import com.carly.model.collection.Car;
import com.carly.model.dto.CarDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CarService {
    void createCar(Authentication authentication, CarDTO carDTO);

    CarDTO deleteCar(Authentication authentication, String carId);

    List<CarDTO> getAllCars(Authentication authentication);

    Car getCar(String carId);

    void changeCarOwner(String carId, String ownerId);
}
