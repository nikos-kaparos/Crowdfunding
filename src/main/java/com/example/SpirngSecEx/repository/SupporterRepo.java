package com.example.SpirngSecEx.repository;

import com.example.SpirngSecEx.model.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupporterRepo extends JpaRepository<Supporter, Integer> {
}
