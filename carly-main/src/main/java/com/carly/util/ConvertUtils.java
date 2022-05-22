package com.carly.util;

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

    public static UserDTO toUserDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setTelephone(user.getTelephone());
        userDto.setRoles(user.getRoles());
        userDto.setVerified(user.isUserVerified());
        return userDto;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setTelephone(userDTO.getTelephone());
        user.setUserVerified(userDTO.isVerified());
        user.setRoles(userDTO.getRoles());
        return user;
    }

}
