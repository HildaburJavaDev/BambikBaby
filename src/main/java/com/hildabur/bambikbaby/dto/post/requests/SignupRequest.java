package com.hildabur.bambikbaby.dto.post.requests;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstname;
    private String lastname;
    private String patronymic;
    private String phoneNumber;
    private String password;
    private String roleName;
}
