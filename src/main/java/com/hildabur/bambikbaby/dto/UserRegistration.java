package com.hildabur.bambikbaby.dto;

import lombok.Data;

@Data
public class UserRegistration {
    private String firstname;
    private String lastname;
    private String patronymic;
    private String phoneNumber;
    private String password;
    private String roleName;
}
