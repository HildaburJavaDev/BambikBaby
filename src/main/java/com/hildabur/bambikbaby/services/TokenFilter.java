package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ExtendedUserDetailsService extendedUserDetailsService;

    public TokenFilter(JWTService jwtService, ExtendedUserDetailsService extendedUserDetailsService) {
        this.jwtService = jwtService;
        this.extendedUserDetailsService = extendedUserDetailsService;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        String jwt = extractJwtFromRequest(request);
        if (jwt != null) {
            try {
                Long userId = jwtService.getUserIdFromToken(jwt);
                UserDetails userDetails = extendedUserDetailsService.loadUserById(userId);
                if (userDetails != null) {
                    if (jwtService.getRoleNameFromToken(jwt).equals("admin")) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException ex) {
                handleExpiredJwtException(ex, response);
                return;
            } catch (JwtException ex) {
                handleJwtException(ex, response);
                return;
            } catch (UserNotFoundException ex) {
                handleUserNotFoundException(ex, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    private void handleExpiredJwtException(ExpiredJwtException ex, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(ex.getMessage());
    }

    private void handleJwtException(JwtException ex, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(ex.getMessage());
    }

    private void handleUserNotFoundException(UserNotFoundException ex, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("User not found: " + ex.getMessage());
    }
    private String extractJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
