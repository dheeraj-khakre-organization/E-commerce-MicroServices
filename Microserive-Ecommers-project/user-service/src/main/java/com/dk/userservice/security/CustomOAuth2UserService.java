package com.dk.userservice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);
        Map<String, Object> attributes = user.getAttributes();

        String provider = request.getClientRegistration().getRegistrationId(); // "google" or "github"
        String email = provider.equals("google") ? (String) attributes.get("email")
                : provider.equals("github") ? (String) attributes.get("login") + "@github.com"
                : null;

        assert email != null;
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE")),
                Map.of("email", email),
                "email"
        );
    }
}