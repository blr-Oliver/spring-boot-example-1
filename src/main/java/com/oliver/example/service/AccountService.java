package com.oliver.example.service;

import com.oliver.example.entity.Account;
import com.oliver.example.entity.User;
import com.oliver.example.repository.AccountRepository;
import com.oliver.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public Account register(String email, String password, boolean createUser) {
    Account newAccount = new Account();
    newAccount.setEmail(email);
    newAccount.setPassword(passwordEncoder.encode(password));
    newAccount = accountRepository.save(newAccount);
    if (createUser) {
      User newUser = new User();
      newUser.setAccountId(newAccount.getId());
      newUser.setAccount(newAccount);
      newUser = userRepository.save(newUser);
      newAccount.setUser(newUser);
    }
    return newAccount;
  }
}
