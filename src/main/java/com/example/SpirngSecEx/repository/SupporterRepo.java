package com.example.SpirngSecEx.repository;

import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupporterRepo extends JpaRepository<Supporter, Integer> {
    Optional<Supporter> findByUsername(String username);

}
