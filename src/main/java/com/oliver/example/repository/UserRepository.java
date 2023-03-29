package com.oliver.example.repository;

import com.oliver.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.login = ?1")
  Optional<User> loadWithRoles(String login);
}
