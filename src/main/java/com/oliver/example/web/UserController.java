package com.oliver.example.web;

import com.oliver.example.entity.Track;
import com.oliver.example.entity.User;
import com.oliver.example.repository.AlbumRepository;
import com.oliver.example.repository.TrackRepository;
import com.oliver.example.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
    path = "/api/users",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Users")
public class UserController {
  @Autowired
  private UserRepository repository;
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private TrackRepository trackRepository;

  @RequestMapping(method = RequestMethod.GET)
  public List<User> listAll() {
    return repository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public User create(@RequestBody User newEntity) {
    newEntity.setLogin(null);
    // TODO manage relations
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public User read(@PathVariable(name = "id", required = true) Integer id) {
    return repository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public User update(@PathVariable(name = "id", required = true) Integer id, @RequestBody User entity) {
    entity.setAccountId(id);
    Set<Integer> trackIds = entity.getFavoriteTracks()
        .stream()
        .map(Track::getId)
        .collect(Collectors.toSet());
    entity.setFavoriteTracks(new HashSet<>(trackRepository.findAllById(trackIds)));
    entity.setFavoriteAlbums(albumRepository.filterExistingIds(entity.getFavoriteAlbums()));
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    repository.deleteById(id);
  }
}
