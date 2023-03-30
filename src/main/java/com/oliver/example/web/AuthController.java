package com.oliver.example.web;

import com.oliver.example.entity.Account;
import com.oliver.example.repository.AccountRepository;
import com.oliver.example.service.AccountService;
import com.oliver.example.web.dto.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(
      method = "POST",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = {
              @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginDto.class)),
              @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE, schema = @Schema(implementation = LoginDto.class))
          }
      ),
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class)))
      }
  )
  @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account registerWithForm(@Parameter(hidden = true) LoginDto registration, HttpServletResponse response) {
    return doRegister(registration, response);
  }

  @Operation(hidden = true)
  @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account registerWithJson(@RequestBody LoginDto registration, HttpServletResponse response) {
    return doRegister(registration, response);
  }

  private Account doRegister(LoginDto registration, HttpServletResponse response) {
    Optional<Account> existing = accountRepository.findByEmail(registration.getUsername());
    if (existing.isPresent()) {
      response.setStatus(HttpStatus.CONFLICT.value());
      return null;
    } else {
      return accountService.register(registration.getUsername(), registration.getPassword(), true);
    }
  }
}
