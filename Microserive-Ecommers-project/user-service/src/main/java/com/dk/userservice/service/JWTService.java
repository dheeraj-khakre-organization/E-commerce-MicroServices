package com.dk.userservice.service;

import com.dk.userservice.dtos.LoginRequest;
import com.dk.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {
    private final JwtUtil jwtUtil;
    private  final AuthenticationManager authenticate;


    public String  getJWTToken(LoginRequest loginRequest){
       try{
           Authentication auth = authenticate.authenticate(
                   new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
           return jwtUtil.generateToken(loginRequest.getUsername());
       }catch (Exception e) {
           log.error("Authentication failed for user {}: {}", loginRequest.getUsername(), e.getMessage());
           throw new RuntimeException("Invalid username or password");
       }

    }
}
