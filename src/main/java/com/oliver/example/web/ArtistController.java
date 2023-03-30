package com.oliver.example.web;

import com.oliver.example.entity.Artist;
import com.oliver.example.entity.Track;
import com.oliver.example.repository.ArtistRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(
    path = "/api/artists",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Artists")
public class ArtistController {
  @Autowired
  private ArtistRepository repository;

  @RequestMapping(method = RequestMethod.GET)
  public List<Artist> listAll() {
    return repository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Artist create(@RequestBody Artist newEntity) {
    newEntity.setId(null);
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public Artist read(@PathVariable(name = "id", required = true) Integer id) {
    return repository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Artist update(@PathVariable(name = "id", required = true) Integer id, @RequestBody Artist entity) {
    entity.setId(id);
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    repository.deleteById(id);
  }

  @RequestMapping(path = "/{id}/tracks", method = RequestMethod.GET)
  public Collection<Track> getTracks(@PathVariable Integer id) {
    return repository
        .findById(id)
        .map(Artist::getTracks)
        .orElse(Collections.emptyList());
  }
}
