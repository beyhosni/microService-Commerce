package com.example.gateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf(csrf->csrf.disable());
    http.authorizeExchange(ex-> ex.pathMatchers("/actuator/**","/").permitAll()
        .anyExchange().authenticated());
    http.oauth2Login(o->{});
    http.oauth2Client();
    return http.build();
  }
}
