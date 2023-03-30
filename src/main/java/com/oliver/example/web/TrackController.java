package com.oliver.example.web;

import com.oliver.example.entity.Album;
import com.oliver.example.entity.Artist;
import com.oliver.example.entity.Track;
import com.oliver.example.repository.ArtistRepository;
import com.oliver.example.repository.TrackRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
    path = "/api/tracks",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Tracks")
public class TrackController {
  @Autowired
  private TrackRepository repository;
  @Autowired
  private ArtistRepository artistRepository;

  @RequestMapping(method = RequestMethod.GET)
  public List<Track> listAll() {
    return repository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Track create(@RequestBody Track newEntity) {
    newEntity.setId(null);
    // TODO manage authors
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public Track read(@PathVariable(name = "id", required = true) Integer id) {
    return repository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Transactional(Transactional.TxType.REQUIRED)
  public Track update(@PathVariable(name = "id", required = true) Integer id, @RequestBody Track entity) {
    entity.setId(id);
    List<Integer> trackIds = entity.getAuthors()
        .stream()
        .map(Artist::getId)
        .collect(Collectors.toList());
    entity.setAuthors(new HashSet<>(artistRepository.findAllById(trackIds)));
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    repository.deleteById(id);
  }

  @RequestMapping(path = "/{id}/albums", method = RequestMethod.GET)
  public Collection<Album> getAlbums(@PathVariable Integer id) {
    return repository
        .findById(id)
        .map(Track::getAlbums)
        .orElse(Collections.emptyList());
  }
}
