package com.hildabur.bambikbaby.controllers;

import com.hildabur.bambikbaby.dto.post.requests.Signup;
import com.hildabur.bambikbaby.exceptions.UserAlreadyExistException;
import com.hildabur.bambikbaby.exceptions.UserRegistrationException;
import com.hildabur.bambikbaby.exceptions.UserRoleNotExistException;
import com.hildabur.bambikbaby.models.User;
import com.hildabur.bambikbaby.services.AuthService;
import com.hildabur.bambikbaby.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private PasswordEncoder passwordEncoder;
    private AuthService authService;
    private JWTService jwtService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registration(@RequestBody Signup signupRequest) {
        System.out.println("hhhhh");
        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = new User();
        String userRole = signupRequest.getRoleName();
        user.setFirstname(signupRequest.getFirstname());
        user.setLastname(signupRequest.getLastname());
        user.setPatronymic(signupRequest.getPatronymic());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setPassword(hashedPassword);
        try {
            System.out.println("popal");
            authService.register(user, userRole);
        } catch (UserAlreadyExistException | UserRoleNotExistException exception) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (UserRegistrationException exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        System.out.println("ghjgkbgkkhjb");
        return ResponseEntity.ok("hk");
    }
}
