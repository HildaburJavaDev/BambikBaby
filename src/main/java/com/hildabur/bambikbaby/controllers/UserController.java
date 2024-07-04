package com.hildabur.bambikbaby.controllers;

import com.hildabur.bambikbaby.dto.post.requests.ChangePasswordRequest;
import com.hildabur.bambikbaby.dto.post.responses.ChangePasswordResponse;
import com.hildabur.bambikbaby.services.UserDetailsImpl;
import com.hildabur.bambikbaby.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test")
    public String test(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails.toString());
        return "hello";
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        try {
            return userService.changePassword((UserDetailsImpl) authentication.getPrincipal(), changePasswordRequest)
                    ? ResponseEntity.ok(new ChangePasswordResponse("Пароль успешно обновлен"))
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ChangePasswordResponse("Неверный текущий пароль"));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
