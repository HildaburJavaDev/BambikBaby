package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dao.UserRoleRepository;
import com.hildabur.bambikbaby.exceptions.UserAlreadyExistException;
import com.hildabur.bambikbaby.exceptions.UserRegistrationException;
import com.hildabur.bambikbaby.exceptions.UserRoleNotExistException;
import com.hildabur.bambikbaby.models.User;
import com.hildabur.bambikbaby.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public void register(User candidate, String roleName) throws UserAlreadyExistException, UserRoleNotExistException, UserRegistrationException {
        System.out.println("(((((");
        if (userRepository.existsByPhoneNumber(candidate.getPhoneNumber())) {
            System.out.println("bad!");
                throw new UserAlreadyExistException("Пользователь с таким номером телефона уже зарегистрирован");
        }
        UserRole userRole = userRoleRepository.findByName(roleName);
        if (userRole == null) {
            System.out.println("bad!!!!");
            throw new UserRoleNotExistException(String.format("Роли пользователя %s не существует", roleName));
        }
        System.out.println(userRole.toString());
        candidate.setUserRole(userRole);
        try {
            userRepository.save(candidate);
            System.out.println(candidate.toString());
        } catch (DataAccessException exception) {
            System.out.println("HERERERERE!");
            throw new UserRegistrationException(exception.getMessage());
        }
    }
}
