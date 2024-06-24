package com.hildabur.bambikbaby.dto.post.responses;

import lombok.Data;

@Data
public class Auth {
    private String token;

    public Auth(String token) {
        this.token = token;
    }
}
