package com.oliver.example.web;

import com.oliver.example.entity.Author;
import com.oliver.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
    path = "/api/authors",
    method = {RequestMethod.HEAD, RequestMethod.OPTIONS},
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorController {
  @Autowired
  private AuthorRepository repository;

  @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
  public List<Author> listAll() {
    return repository.findAll();
  }

  @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Author create(@RequestBody Author newEntity) {
    newEntity.setId(null);
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public Author read(@PathVariable(name = "id", required = true) Integer id) {
    return repository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Author update(@PathVariable(name = "id", required = true) Integer id, @RequestBody Author entity) {
    entity.setId(id);
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    repository.deleteById(id);
  }
}