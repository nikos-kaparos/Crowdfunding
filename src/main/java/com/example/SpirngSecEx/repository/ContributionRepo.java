package com.example.SpirngSecEx.repository;

import com.example.SpirngSecEx.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepo extends JpaRepository<Contribution, Integer> {

    @Modifying
    @Query("DELETE FROM Contribution c WHERE c.project.id = :projectId")
    void deleteByProjectId(int projectId);
}
