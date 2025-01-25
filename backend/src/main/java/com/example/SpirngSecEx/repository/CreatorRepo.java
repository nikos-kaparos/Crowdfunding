package com.example.SpirngSecEx.repository;

import com.example.SpirngSecEx.model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepo extends JpaRepository<Creator, Integer> {
    Creator findByUsername(String username);
    Creator findByEmail(String email);
}
