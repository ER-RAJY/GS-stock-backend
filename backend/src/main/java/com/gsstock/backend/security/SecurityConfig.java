package com.gsstock.backend.security;

import com.gsstock.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/produits/**").hasAnyRole("ADMIN", "COMPTABLE")
                .requestMatchers("/api/movements/**").hasAnyRole("ADMIN", "COMPTABLE")
                .requestMatchers("/api/achats/**").hasRole("ADMIN")
                .requestMatchers("/api/stats/**").hasRole("ADMIN")
                .requestMatchers("/api/stats/stock/**").hasAnyRole("ADMIN", "COMPTABLE")
                .requestMatchers("/api/produits/alerts").hasAnyRole("ADMIN", "COMPTABLE")
                .requestMatchers("/api/export/**").hasRole("ADMIN")
                .requestMatchers("/api/export/**").hasRole("ADMIN")

                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

}
