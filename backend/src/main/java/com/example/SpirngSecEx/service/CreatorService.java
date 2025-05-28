package com.example.SpirngSecEx.service;

import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.repository.CreatorRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorService {

    private  CreatorRepo creatorRepository;

    public CreatorService(CreatorRepo creatorRepository) {
        this.creatorRepository = creatorRepository;
    }
    @Transactional
    public List<Creator> getAllCreators() {
        return creatorRepository.findAll();
    }
    @Transactional
    public void saveCreator(Creator creator) {
        creatorRepository.save(creator);
    }

    @Transactional
    public Creator getCreatorById(int id) {
        return creatorRepository.findById(id).get();
    }

    // Διόρθωση εδώ: Μετονομασία σε findByUsername
    @Transactional
    public Creator findByUsername(String username) {
        return creatorRepository.findByUsername(username);
    }

    @Transactional
    public void deleteCreator(int id) {creatorRepository.deleteById(id);}
}
