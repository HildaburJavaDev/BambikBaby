package com.hildabur.bambikbaby.dao;

import com.hildabur.bambikbaby.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findUserById(Long id);
}
