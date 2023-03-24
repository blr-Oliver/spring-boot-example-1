package com.oliver.example.repository;

import com.oliver.example.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
  @Query("from Album a join a.tracks t where t.id = ?1")
  Collection<Album> findAllByTrackId(Integer trackId);

  @Query("select a.id from Album a where a.id in ?1")
  Set<Integer> filterExistingIds(Collection<Integer> albumIds);
}
