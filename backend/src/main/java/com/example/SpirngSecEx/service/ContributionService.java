package com.example.SpirngSecEx.service;

import com.example.SpirngSecEx.model.Contribution;
import com.example.SpirngSecEx.repository.ContributionRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ContributionService {

    private ContributionRepo contributionRepository;

    public ContributionService(ContributionRepo contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Transactional
    public void saveContribution (Contribution contribution){
        contributionRepository.save(contribution);
    }
}
