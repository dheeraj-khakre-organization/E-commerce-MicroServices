package com.dk.userservice.security;

import com.dk.userservice.models.User;
import com.dk.userservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
@Lazy
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public PasswordEncoder getPasswordEncoder(PasswordEncoder passwordEncoder) {
        return passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String provider = oauthToken.getAuthorizedClientRegistrationId(); // "google", "github", etc.

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            String firstName = (name != null) ? name.split(" ")[0] : email.split("@")[0];
            String lastName = (name != null && name.split(" ").length > 1) ? name.split(" ")[1] : "unknown";

            if (email == null) {
                writeErrorResponse(response, "Email not found in OAuth2 response", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Optional<Object> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = (User) userOptional.get();
                String token = jwtUtil.generateToken(user.getUserName());
                writeSuccessResponse(response, token);
                log.info("Existing user token: {}", token);
            } else {
                String password = UUID.randomUUID().toString().replace("-", "").substring(0, 12);

                User user = userRepository.save(User.builder()
                        .email(email)
                        .userName(email.split("@")[0])
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(password) // Consider encoding if needed
                        .provider(provider.toUpperCase())
                        .active(true)
                        .roles(Collections.singletonList("ROLE"))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());

                String token = jwtUtil.generateToken(user.getUserName());
                writeSuccessResponse(response, token);
                log.info("New user token: {} | User info: {}", token, user);
            }

        } catch (Exception e) {
            log.error("OAuth2 login error: {}", e.getMessage(), e);
            writeErrorResponse(response, "OAuth2 login failed", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void writeSuccessResponse(HttpServletResponse response, String token) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

    private void writeErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"timestamp\": \"" + LocalDateTime.now() + "\", \"status\": " + status + ", \"error\": \"" + message + "\"}");
    }
}