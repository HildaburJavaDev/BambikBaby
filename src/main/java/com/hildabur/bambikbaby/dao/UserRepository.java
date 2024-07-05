package com.hildabur.bambikbaby.dao;

import com.hildabur.bambikbaby.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    Optional<User> findById(int id);
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findUserById(Long id);
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
    int updatePasswordById(@Param("userId") Long userId, @Param("password") String password);
    @Query("SELECT u.password FROM User u WHERE u.id = :userId")
    Optional<String> findPasswordById(Long userId);
}
