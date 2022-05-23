package com.carly.controller;

import com.carly.model.dto.CarDTO;
import com.carly.security.service.AuthorizationService;
import com.carly.service.CarService;
import com.carly.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@PreAuthorize("hasRole('USER')")
public class CarController {

	private final CarService carService;

	@Autowired
	public CarController(CarServiceImpl carService, AuthorizationService authorizationService) {
		this.carService = carService;
	}

	@GetMapping()
	public List<CarDTO> getAllCars(Authentication auth) {
		return carService.getAllCars(auth);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public void createCar(Authentication auth, @RequestBody CarDTO carDTO) {
		carService.createCar(auth, carDTO);
	}

	@DeleteMapping("/{carId}")
	public CarDTO deleteCar(Authentication auth, @PathVariable String carId) {
		return carService.deleteCar(auth, carId);
	}

}
