package com.hildabur.bambikbaby.dto;

import com.hildabur.bambikbaby.models.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String phoneNumber;
    private UserRole userRole;
}
