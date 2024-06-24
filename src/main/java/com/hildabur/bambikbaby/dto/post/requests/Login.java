package com.hildabur.bambikbaby.dto.post.requests;

import lombok.Data;

@Data
public class Login {
    private String phoneNumber;
    private String password;
}
