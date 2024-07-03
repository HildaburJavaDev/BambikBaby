package com.hildabur.bambikbaby.controllers;

import com.hildabur.bambikbaby.dto.post.requests.SigninRequest;
import com.hildabur.bambikbaby.dto.post.requests.SignupRequest;
import com.hildabur.bambikbaby.dto.post.responses.AuthResponse;
import com.hildabur.bambikbaby.exceptions.AuthenticationException;
import com.hildabur.bambikbaby.exceptions.UserAlreadyExistException;
import com.hildabur.bambikbaby.exceptions.UserRegistrationException;
import com.hildabur.bambikbaby.exceptions.UserRoleNotExistException;
import com.hildabur.bambikbaby.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {


    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registration(@RequestBody SignupRequest signupRequest) {
        try {
            return ResponseEntity.ok(authService.register(signupRequest));
        } catch (UserAlreadyExistException | UserRoleNotExistException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (UserRegistrationException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        try {
            return ResponseEntity.ok(new AuthResponse(authService.authenticateAndGenerateToken(signinRequest)));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }
    }
}
