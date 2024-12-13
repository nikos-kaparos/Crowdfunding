package com.example.SpirngSecEx.service;

import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.repository.CreatorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorService {

    private  CreatorRepo creatorRepository;

    public CreatorService(CreatorRepo creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    public List<Creator> getAllCreators() {
        return creatorRepository.findAll();
    }

    public void saveCreator(Creator creator) {
        creatorRepository.save(creator);
    }

    public Creator getCreatorById(int id) {
        return creatorRepository.findById(id).get();
    }

    // Διόρθωση εδώ: Μετονομασία σε findByUsername
    public Creator findByUsername(String username) {
        return creatorRepository.findByUsername(username);
    }
}
