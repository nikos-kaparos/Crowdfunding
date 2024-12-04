package com.example.SpirngSecEx.repository;

import com.example.SpirngSecEx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    boolean existsByUsername(String username);
    Users findByRole(String role);
}
