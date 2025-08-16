package com.ecommerce.apigateway.service;



import com.ecommerce.apigateway.dtos.UserResponse;
import com.ecommerce.apigateway.extrurnal.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service("userDetailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

     @Autowired
     private final UserClient userClient;
     private final PasswordEncoder passwordEncoder;

//    public UserDetailsServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserClient userClient) {
//        this.userClient = userClient;
//        this.passwordEncoder = passwordEncoder;
//    }


    public UserDetailsServiceImpl(UserClient userClient, @Lazy PasswordEncoder passwordEncoder) {
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Mono<UserDetails> findByUsername(String username) {

        UserResponse userResponse = userClient.getUserByName(username); // blocking call
       log.info("after {} ....{}......{}",userResponse.getPassword(),userResponse.getEmail(),username);
        if (userResponse == null) {
            return Mono.error(new UsernameNotFoundException("User not found: " + username));
        }
        log.info("dddddddddddddddd{}", userResponse.toString());

        return Mono.just(User.builder()
                .username(userResponse.getUserName())
                .password(userResponse.getPassword())
                .authorities(userResponse.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build());
    }

}