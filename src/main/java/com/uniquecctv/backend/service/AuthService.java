package com.uniquecctv.backend.service;

import com.uniquecctv.backend.dto.AuthResponse;
import com.uniquecctv.backend.dto.LoginRequest;
import com.uniquecctv.backend.dto.RegisterRequest;
import com.uniquecctv.backend.entity.User;
import com.uniquecctv.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Get UserDetails with proper authorities
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        
        // Generate token with roles included
        String jwt = jwtUtil.generateToken(userDetails);
        
        User user = userService.getUserByEmail(loginRequest.getEmail());
        
        return new AuthResponse(jwt, userService.getUserResponse(user));
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        User user = userService.createUser(registerRequest);
        
        // Get UserDetails with proper authorities for token generation
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        
        return new AuthResponse(jwt, userService.getUserResponse(user));
    }
}