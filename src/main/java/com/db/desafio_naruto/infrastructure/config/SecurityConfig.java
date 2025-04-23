package com.db.desafio_naruto.infrastructure.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.db.desafio_naruto.infrastructure.handler.dto.ErrorResponse;
import com.db.desafio_naruto.infrastructure.security.JwtRequestFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, e) -> {
                    gerarResponseAcessoNaoAutorizado(response);
                })
                .accessDeniedHandler((request, response, e) -> {
                    gerarResponseAcessoNegado(response);
                })
            );

        return http.build();
    }

    private HttpServletResponse gerarResponseAcessoNegado(HttpServletResponse response) throws JsonProcessingException, IOException  {
        response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                            new ErrorResponse(403, "Acesso negado", "Você não tem permissão para acessar este recurso")
                        )
                    );
        return response;
    }

    private HttpServletResponse gerarResponseAcessoNaoAutorizado(HttpServletResponse response) throws JsonProcessingException, IOException  {
        response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                            new ErrorResponse(401, "Não autorizado", "Token inválido ou ausente")
                        )
                    );
        return response;
    }
}