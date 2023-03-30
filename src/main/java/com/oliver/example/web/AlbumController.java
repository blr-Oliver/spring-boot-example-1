package com.oliver.example.web;

import com.oliver.example.entity.Album;
import com.oliver.example.entity.Track;
import com.oliver.example.repository.AlbumRepository;
import com.oliver.example.repository.TrackRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
    path = "/api/albums",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Albums")
public class AlbumController {
  @Autowired
  private AlbumRepository repository;
  @Autowired
  private TrackRepository trackRepository;

  @RequestMapping(method = RequestMethod.GET)
  public List<Album> listAll() {
    return repository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Album create(@RequestBody Album newEntity) {
    newEntity.setId(null);
    // TODO manage tracks
    return repository.save(newEntity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public Album read(@PathVariable(name = "id", required = true) Integer id) {
    return repository.findById(id).orElse(null);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Transactional(Transactional.TxType.REQUIRED)
  public Album update(@PathVariable(name = "id", required = true) Integer id, @RequestBody Album entity) {
    entity.setId(id);
    List<Integer> trackIds = entity.getTracks()
        .stream()
        .map(Track::getId)
        .collect(Collectors.toList());
    entity.setTracks(trackRepository.findAllById(trackIds));
    return repository.save(entity);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(name = "id", required = true) Integer id) {
    repository.deleteById(id);
  }
}
