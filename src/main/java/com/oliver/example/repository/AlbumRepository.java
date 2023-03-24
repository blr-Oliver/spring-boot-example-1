package com.oliver.example.repository;

import com.oliver.example.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
  @Query("from Album a join a.tracks t where t.id = ?1")
  Collection<Album> findAllByTrackId(Integer trackId);
}
