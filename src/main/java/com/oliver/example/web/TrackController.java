package com.oliver.example.web;

import com.oliver.example.entity.Track;
import com.oliver.example.repository.TrackRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
    path = "/api/tracks",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Tracks")
public class TrackController {
  @Autowired
  private TrackRepository repository;

  @RequestMapping(method = RequestMethod.GET)
  public List<Track> listAll() {
    return repository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Track create(@RequestBody Track newEntity) {
    newEntity.setId(null);
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public Track read(@PathVariable(name = "id", required = true) Integer id) {
    return repository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Track update(@PathVariable(name = "id", required = true) Integer id, @RequestBody Track entity) {
    entity.setId(id);
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    repository.deleteById(id);
  }
}
