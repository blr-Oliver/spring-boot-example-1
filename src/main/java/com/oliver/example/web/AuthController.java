package com.oliver.example.web;

import com.oliver.example.entity.Account;
import com.oliver.example.repository.AccountRepository;
import com.oliver.example.service.AccountService;
import com.oliver.example.web.dto.LoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@Tag(name = "Auth")
public class AuthController {
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountService accountService;

  @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public void login(@RequestBody LoginDto login) {
    // this will never be called
  }

  @RequestMapping(path = "/logout", method = RequestMethod.POST)
  public void logout() {
    // this will never be called
  }

  @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public Account register(LoginDto registration, HttpServletResponse response) {
    Optional<Account> existing = accountRepository.findByEmail(registration.getUsername());
    if (existing.isPresent()) {
      response.setStatus(HttpStatus.CONFLICT.value());
      return null;
    } else {
      return accountService.register(registration.getUsername(), registration.getPassword(), true);
    }
  }
}
