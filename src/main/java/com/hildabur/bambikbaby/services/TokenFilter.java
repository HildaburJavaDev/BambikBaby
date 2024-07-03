package com.hildabur.bambikbaby.services;

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
            } catch (Exception ex) {
                System.out.println("Exception occurred while processing JWT: " + ex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
