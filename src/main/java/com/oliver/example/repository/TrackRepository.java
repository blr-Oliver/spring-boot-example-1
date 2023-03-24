package com.oliver.example.repository;

import com.oliver.example.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
  @Query("from Track t join t.authors a where a.id = ?1")
  Collection<Track> findAllByAuthorId(Integer artistId);
}
