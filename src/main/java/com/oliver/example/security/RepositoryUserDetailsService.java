package com.oliver.example.security;

import com.oliver.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUserDetailsService implements UserDetailsService {
  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return accountRepository
        .findByEmail(username)
        .map(account -> User.builder()
            .username(account.getEmail())
            .password(account.getPassword())
            .roles(account.getRoles() == null ? new String[0] : account.getRoles().toArray(new String[0]))
            .accountLocked(account.isLocked())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
