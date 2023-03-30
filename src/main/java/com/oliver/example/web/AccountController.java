package com.oliver.example.web;

import com.oliver.example.entity.Account;
import com.oliver.example.repository.AccountRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts")
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;

  @RequestMapping(method = RequestMethod.GET)
  public List<Account> listAll() {
    return accountRepository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Account create(@RequestBody Account newEntity) {
    newEntity.setId(null);
    // TODO manage password encoding
    return accountRepository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public Account read(@PathVariable(name = "id", required = true) Integer id) {
    return accountRepository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Account update(@PathVariable(name = "id", required = true) Integer id, @RequestBody Account entity) {
    entity.setId(id);
    // TODO manage password encoding
    return accountRepository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    accountRepository.deleteById(id);
  }

}
