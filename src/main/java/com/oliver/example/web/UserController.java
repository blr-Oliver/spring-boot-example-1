package com.oliver.example.web;

import com.oliver.example.entity.User;
import com.oliver.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
    path = "/api/users",
    method = {RequestMethod.HEAD, RequestMethod.OPTIONS},
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
  @Autowired
  private UserRepository repository;

  @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
  public List<User> listAll() {
    return repository.findAll();
  }

  @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public User create(@RequestBody User newEntity) {
    newEntity.setLogin(null);
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public User read(@PathVariable(name = "id", required = true) String login) {
    return repository.findById(login).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public User update(@PathVariable(name = "id", required = true) String login, @RequestBody User entity) {
    entity.setLogin(login);
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(@PathVariable(name = "id", required = true) String login) {
    repository.deleteById(login);
  }
}
