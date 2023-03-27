package com.oliver.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(this::configureAuthorization)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .formLogin(this::configureFormLogin)
        .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
        .httpBasic().disable()
        .csrf().disable()
        .cors().disable()
    ;
    return http.build();
  }
  private void configureAuthorization(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
    config
        .antMatchers("/swagger-ui/**", "/*/api-docs/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .antMatchers("/api/users/**").hasRole("ADMIN")
        .antMatchers("/api/**").authenticated()
        .antMatchers(HttpMethod.POST, "/login", "/logout").permitAll()
        .anyRequest().authenticated();
  }

  private void configureFormLogin(FormLoginConfigurer<HttpSecurity> config) {
    config
        .loginPage("/login")
        .successHandler(noopAuthenticationSuccessHandler())
        .failureHandler(entryPointAuthenticationFailureHandler());
  }

  @Bean
  public HttpStatusEntryPoint restAuthenticationEntryPoint() {
    return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
  }

  @Bean
  public AuthenticationSuccessHandler noopAuthenticationSuccessHandler() {
    return (request, response, authentication) -> {
    };
  }

  @Bean
  public AuthenticationFailureHandler entryPointAuthenticationFailureHandler() {
    return new AuthenticationEntryPointFailureHandler(restAuthenticationEntryPoint());
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.withUsername("user")
        .password(passwordEncoder.encode("password"))
        .roles("USER")
        .build();

    UserDetails admin = User.withUsername("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}