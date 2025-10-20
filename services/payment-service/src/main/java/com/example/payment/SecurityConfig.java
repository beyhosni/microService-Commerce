package com.example.payment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableMethodSecurity
public class SecurityConfig {
  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable());
    http.authorizeHttpRequests(auth->auth
      .requestMatchers("/actuator/**").permitAll()
      .requestMatchers("/products/**").permitAll() // catalog open for demo
      .anyRequest().authenticated());
    http.oauth2ResourceServer(o->o.jwt());
    return http.build();
  }
}
