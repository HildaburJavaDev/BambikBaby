package com.hildabur.bambikbaby.mappers;

import com.hildabur.bambikbaby.dto.UserDTO;
import com.hildabur.bambikbaby.models.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId((long) user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setPatronymic(user.getPatronymic());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setUserRole(user.getUserRole());
        return userDto;
    }
}
