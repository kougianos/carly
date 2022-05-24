package com.carly.service;

import com.carly.exception.ResourceNotFoundException;
import com.carly.model.collection.Car;
import com.carly.model.collection.User;
import com.carly.model.dto.CarDTO;
import com.carly.repository.CarRepository;
import com.carly.security.UserDetailsImpl;
import com.carly.util.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserService userService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, UserService userService) {
        this.carRepository = carRepository;
        this.userService = userService;
    }

    @Override
    public void createCar(Authentication auth, CarDTO carDTO) {
        Car car = ConvertUtils.toCar(carDTO);
        String userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        User user = userService.getUser(userId);
        car.setOwnerId(userId);
        String carId = carRepository.save(car).getId();
        log.info("Car created: {}", car);
        userService.addCar(user.getId(), carId);
    }

    @Override
    public CarDTO deleteCar(Authentication auth, String carId) {
        String userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        userService.getUser(userId);
        Car car = getCar(carId);
        carRepository.deleteById(car.getId());
        log.info("Car deleted: {}", car);
        userService.removeCar(userId, car.getId());
        return ConvertUtils.toCarDTO(car);
    }

    @Override
    public List<CarDTO> getAllCars(Authentication auth) {
        String userId = ((UserDetailsImpl) auth.getPrincipal()).getId();
        User user = userService.getUser(userId);

        List<Car> cars = carRepository.findByOwnerId(user.getId());
        return cars.stream()
                .map(ConvertUtils::toCarDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Car getCar(String carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Car %s not found", carId)));
    }

    @Override
    public void changeCarOwner(String carId, String ownerId) {
        Car car = getCar(carId);
        User user = userService.getUser(ownerId);
        car.setOwnerId(user.getId());
        carRepository.save(car);
        log.info("Owner for car {} changed to {}", carId, ownerId);
    }
}
