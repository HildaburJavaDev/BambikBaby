package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ExtendedUserDetailsService extends UserDetailsService {
    UserDetails loadUserById(Long id) throws UserNotFoundException;
}
