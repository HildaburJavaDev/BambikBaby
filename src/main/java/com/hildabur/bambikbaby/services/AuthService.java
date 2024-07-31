package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dao.UserRoleRepository;
import com.hildabur.bambikbaby.dto.SigninRequest;
import com.hildabur.bambikbaby.dto.SignupRequest;
import com.hildabur.bambikbaby.exceptions.*;
import com.hildabur.bambikbaby.models.User;
import com.hildabur.bambikbaby.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    private JWTService jwtService;

    @Autowired
    public void setJwtService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public User register(SignupRequest signupRequest) throws UserAlreadyExistException, UserRoleNotExistException, UserRegistrationException {
        User candidate = createUserFromRequest(signupRequest);
        if (checkUserExist(candidate.getPhoneNumber())) {
            throw new UserAlreadyExistException("Пользователь с таким номером телефона уже зарегистрирован");
        }
        UserRole userRole = getUserRole(signupRequest.getRoleName());
        candidate.setUserRole(userRole);
        saveUser(candidate);
        return candidate;
    }

    public User createUserFromRequest(SignupRequest signupRequest) {
        User candidate = new User();
        candidate.setFirstname(signupRequest.getFirstname());
        candidate.setLastname(signupRequest.getLastname());
        candidate.setPatronymic(signupRequest.getPatronymic());
        candidate.setPhoneNumber(signupRequest.getPhoneNumber());
        candidate.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        return candidate;
    }

    private void saveUser(User candidate) throws UserRegistrationException {
        try {
            userRepository.save(candidate);
        } catch (DataAccessException exception) {
            throw new UserRegistrationException(exception.getMessage());
        }
    }
    private UserRole getUserRole(String roleName) throws UserRoleNotExistException {
        UserRole userRole = userRoleRepository.findByName(roleName);
        if (userRole == null) {
            throw new UserRoleNotExistException(String.format("Роли пользователя %s не существует", roleName));
        }
        return userRole;
    }
    private boolean checkUserExist(String phoneNumber) throws DataAccessException {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public Authentication authenticate(SigninRequest signinRequest) throws AuthenticationException {
        User user = userRepository.findByPhoneNumber(signinRequest.getPhoneNumber())
                .orElseThrow(() -> new AuthenticationException("Пользователь не найден"));

        if (passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            UserDetailsImpl userDetails = new UserDetailsImpl((long) user.getId(), user.getPhoneNumber(), user.getUserRole().getName());
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new AuthenticationException("Неверный пароль");
        }
    }

    public String authenticateAndGenerateToken(SigninRequest signinRequest) throws AuthenticationException {
        Authentication authentication = authenticate(signinRequest);
        return jwtService.generateToken(authentication);
    }
}
