package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dto.patch.UpdateUserDTO;
import com.hildabur.bambikbaby.dto.post.requests.ChangePasswordRequest;
import com.hildabur.bambikbaby.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean changePassword(UserDetailsImpl userDetails, ChangePasswordRequest changePasswordRequest) {
        return userRepository.findPasswordById(userDetails.getId())
                .filter(password -> passwordEncoder.matches(changePasswordRequest.getOldPassword(), password))
                .map(password -> {
                    updatePassword(userDetails.getId(), passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                    return true;
                }).orElse(false);
    }

    private void updateUser(User user) {
        userRepository.save(user);
    }
    public boolean updateUserData(UserDetailsImpl userDetails, UpdateUserDTO updateUserDTO, Long id) {
        if ((userDetails.getRoleName().equals("admin")) || (userDetails.getId().equals(id))) {
            User user = userRepository.findUserById(id).orElseThrow(RuntimeException::new);
            if (updateUserDTO.getLastname() != null) {
                user.setLastname(updateUserDTO.getLastname());
            }
            if (updateUserDTO.getPhoneNumber() != null) {
                user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            }
            updateUser(user);
            return true;
        } else {
            return false;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void updatePassword(Long userId, String password) {
        userRepository.updatePasswordById(userId, password);
    }
}
