package com.oliver.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public AuthenticationManager globalAuthenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .rememberMe().disable()
        .csrf().disable()
        .cors().disable()
        .authorizeRequests(this::configureAuthorization)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .formLogin(this::configureFormLogin)
        .logout(this::configureLogout)
        .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
        .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
    ;
    return http.build();
  }
  private void configureAuthorization(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
    config
        .antMatchers("/swagger-ui/**", "/*/api-docs/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .antMatchers("/api/users/**", "/api/accounts/**").hasRole("ADMIN")
        .antMatchers("/api/**").authenticated()
        .antMatchers(HttpMethod.POST, "/login", "/logout").permitAll()
        .antMatchers(HttpMethod.POST, "/register").anonymous()
        .anyRequest().authenticated();
  }

  private void configureFormLogin(FormLoginConfigurer<HttpSecurity> config) {
    config
        .loginPage("/login")
        .successHandler(noopAuthenticationSuccessHandler())
        .failureHandler(simpleAuthenticationFailureHandler());
  }

  private void configureLogout(LogoutConfigurer<HttpSecurity> config) {
    config
        .logoutUrl(null)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST", false))
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
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
  public AuthenticationFailureHandler simpleAuthenticationFailureHandler() {
    return new SimpleUrlAuthenticationFailureHandler();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
