package com.example.SpirngSecEx.repository;

import com.example.SpirngSecEx.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepo extends JpaRepository<Contribution, Integer> {
}
