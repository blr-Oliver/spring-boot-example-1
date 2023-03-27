package com.oliver.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/swagger-ui/**", "/*/api-docs/**").permitAll()
          .antMatchers(HttpMethod.GET, "/api/**").permitAll()
          .antMatchers("/api/**").authenticated()
          .antMatchers(HttpMethod.POST, "/login", "/logout").permitAll()
          .anyRequest().authenticated()
          .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .formLogin().disable()
        .httpBasic().disable()
        .csrf().disable()
        .cors().disable()
    ;
    return http.build();
  }
}
