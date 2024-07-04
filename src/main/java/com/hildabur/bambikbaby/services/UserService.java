package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dto.post.requests.ChangePasswordRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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
            if (userRepository.existsByPhoneNumber(userDetails.getPhoneNumber())
                    && passwordEncoder.matches(changePasswordRequest.getOldPassword(), userRepository.findPasswordById(userDetails.getId()).orElse(""))
            ) {
                updatePassword(userDetails.getId(), passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                return true;
            } else {
                return false;
            }
    }

    @Transactional
    public void updatePassword(Long userId, String password) {
        userRepository.updatePasswordById(userId, password);
    }
}
