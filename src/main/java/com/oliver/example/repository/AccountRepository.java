package com.oliver.example.repository;

import com.oliver.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
  @Query("select a from Account a LEFT JOIN FETCH a.roles where a.email = ?1")
  Optional<Account> findByEmail(String email);
}
