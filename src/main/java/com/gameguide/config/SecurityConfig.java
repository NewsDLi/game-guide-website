package com.gameguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // static resources
                .antMatchers("/css/**", "/js/**", "/images/**", "/uploads/**", "/favicon.ico", "/favicon.svg").permitAll()
                // public pages
                .antMatchers("/", "/index", "/games", "/guides", "/guide-detail",
                                 "/game-detail", "/login", "/guide-editor", "/user-center",
                                 "/about", "/contact", "/disclaimer", "/privacy").permitAll()
                // public API endpoints
                .antMatchers("/api/user/register", "/api/user/login").permitAll()
                .antMatchers("/api/game/**").permitAll()
                // guide list (public) / detail by numeric id (public)
                .antMatchers("/api/guide/list").permitAll()
                .regexMatchers(HttpMethod.GET, "/api/guide/\\d+").permitAll()
                .antMatchers("/api/comment/list").permitAll()
                .antMatchers("/api/tag/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
