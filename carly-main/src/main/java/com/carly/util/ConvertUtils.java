package com.carly.util;

import com.carly.model.collection.Car;
import com.carly.model.dto.CarDTO;
import com.carly.model.dto.UserDTO;
import com.carly.model.collection.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConvertUtils {

    private ConvertUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String convertToJson(Object ob) throws JsonProcessingException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setDateFormat(df);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
    }

    public static String jsonToYaml(String json) throws JsonProcessingException {
        JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
        return new YAMLMapper().writeValueAsString(jsonNodeTree);
    }

    public static UserDTO toUserDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setTelephone(user.getTelephone());
        userDto.setRoles(user.getRoles());
        userDto.setVerified(user.isUserVerified());
        userDto.setOwnedCars(user.getOwnedCars());
        return userDto;
    }

    /**
     * Exclude id from conversion.
     */
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setTelephone(userDTO.getTelephone());
        user.setUserVerified(userDTO.isVerified());
        user.setRoles(userDTO.getRoles());
        user.setOwnedCars(userDTO.getOwnedCars());
        return user;
    }

    public static CarDTO toCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setMileage(car.getMileage());
        carDTO.setModel(car.getModel());
        carDTO.setYear(car.getYear());
        carDTO.setOwnerId(car.getOwnerId());
        return carDTO;
    }

    /**
     * Exclude id from conversion.
     */
    public static Car toCar(CarDTO carDTO) {
        Car car = new Car();
        car.setMileage(carDTO.getMileage());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setOwnerId(carDTO.getOwnerId());
        return car;
    }

}
