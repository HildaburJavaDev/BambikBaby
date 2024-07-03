package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dao.UserRoleRepository;
import com.hildabur.bambikbaby.dto.post.requests.SignupRequest;
import com.hildabur.bambikbaby.exceptions.*;
import com.hildabur.bambikbaby.models.User;
import com.hildabur.bambikbaby.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
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


    public String register(SignupRequest signupRequest) throws UserAlreadyExistException, UserRoleNotExistException, UserRegistrationException {
        User candidate = createUserFromRequest(signupRequest);
        checkUserExist(candidate.getPhoneNumber());
        UserRole userRole = getUserRole(signupRequest.getRoleName());
        candidate.setUserRole(userRole);
        saveUser(candidate);
        return candidate.toString();
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
    private void checkUserExist(String phoneNumber) throws UserAlreadyExistException {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistException("Пользователь с таким номером телефона уже зарегистрирован");
        }
    }

    public UserDetailsImpl authenticate(User candidate) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(candidate.getPhoneNumber());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(candidate.getPassword())) {
                return new UserDetailsImpl((long) user.getId(), user.getPhoneNumber(), user.getUserRole().getName());
            }
            else {
                throw new AuthenticationException("Неверный пароль");
            }
        } else
            throw new AuthenticationException("Пользователь с таким номером телефона не существует");
    }
}
