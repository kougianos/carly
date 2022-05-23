package com.carly.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarDTO {
	private String id;
	private String model;
	private String year;
	private String mileage;
	private String ownerId;
}