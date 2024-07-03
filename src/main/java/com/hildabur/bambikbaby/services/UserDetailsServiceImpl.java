package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.exceptions.UserNotFoundException;
import com.hildabur.bambikbaby.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements ExtendedUserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return new UserDetailsImpl(
                (long) user.getId(),
                user.getPhoneNumber(),
                user.getUserRole().getName());
    }

    public UserDetails loadUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException( "Пользователь не найден"));
        return new UserDetailsImpl(
                (long) user.getId(),
                user.getPhoneNumber(),
                user.getUserRole().getName(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getName())));
    }
}
