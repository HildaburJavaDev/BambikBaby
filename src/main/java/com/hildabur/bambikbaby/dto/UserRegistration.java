package com.hildabur.bambikbaby.dto;

import lombok.Data;

@Data
public class UserRegistration {
    private String firstname;
    private String lastname;
    private String patronimyc;
    private String phoneNumber;
    private String password;
    private String roleName;
}
