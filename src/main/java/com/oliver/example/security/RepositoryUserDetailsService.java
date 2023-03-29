package com.oliver.example.security;

import com.oliver.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired // FIXME already encoded passwords must be stored in database
  private PasswordEncoder encoder;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .loadWithRoles(username)
        .map(user -> User.builder()
            .username(user.getLogin())
            .password(encoder.encode(user.getLogin())) // FIXME already encoded passwords must be stored in database
            .roles(user.getRoles().toArray(new String[0]))
            .build())
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
