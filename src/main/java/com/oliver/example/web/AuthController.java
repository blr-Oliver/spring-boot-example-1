package com.oliver.example.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth")
public class AuthController {

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public void login(@RequestParam(name = "username") String username,
                    @RequestParam(name = "password") String password) {
    // this will never be called
  }

  @RequestMapping(path = "/logout", method = RequestMethod.POST)
  public void logout() {
    // this will never be called
  }
}
