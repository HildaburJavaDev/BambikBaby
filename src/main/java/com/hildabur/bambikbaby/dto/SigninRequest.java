package com.hildabur.bambikbaby.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String phoneNumber;
    private String password;
}
