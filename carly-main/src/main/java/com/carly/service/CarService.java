package com.carly.service;

import com.carly.model.dto.CarDTO;

import java.util.List;

public class CarService implements CrudService<CarDTO> {
	@Override
	public CarDTO findById(String id) {
		return null;
	}

	@Override
	public List<CarDTO> findAll() {
		return null;
	}

	@Override
	public CarDTO patchById(String id, CarDTO resource) {
		return null;
	}

	@Override
	public CarDTO deleteById(String id) {
		return null;
	}
}
