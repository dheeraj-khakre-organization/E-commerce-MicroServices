package com.ecommerce.apigateway.JWT;

import com.ecommerce.apigateway.utility.JwtUtil;
import org.apache.http.HttpHeaders;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtFilter(@Qualifier("userDetailsServiceImpl") ReactiveUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && jwtUtil.validateToken(jwt)) {
                return userDetailsService.findByUsername(username)
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()))
                        .map(auth -> {
                       SecurityContext context = new SecurityContextImpl(auth);
                            return context;
                        })
                        .flatMap(context -> exchange.getPrincipal()
                                .then(Mono.defer(() -> {
                                    exchange.getAttributes().put(SecurityContext.class.getName(), context);
                                    return chain.filter(exchange);
                                })));
            }
        }

        return chain.filter(exchange);
    }
}