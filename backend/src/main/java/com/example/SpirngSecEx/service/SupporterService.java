package com.example.SpirngSecEx.service;

import com.example.SpirngSecEx.model.Contribution;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.repository.SupporterRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupporterService {

    private SupporterRepo supporterRepository;

    public SupporterService(SupporterRepo supporterRepository) {
        this.supporterRepository = supporterRepository;
    }


    @Transactional
    public List<Supporter> getSupporters(){
        return supporterRepository.findAll();
    }

    @Transactional
    public void saveSupporter (Supporter supporter){
        supporterRepository.save(supporter);
    }

    @Transactional
    public Supporter getSupporter(Integer supporterId){
        return supporterRepository.findById(supporterId).get();
    }

    @Transactional
    public void updateSupporterProjectList(Project project, Supporter supporter){
        supporter.addProject(project);
        supporterRepository.save(supporter);
    }

    @Transactional
    public void updateSupporterContributionList (Supporter supporter, Contribution contribution){
        supporter.addContributionToSupporter(contribution);
        supporterRepository.save(supporter);
    }

    @Transactional
    public void deleteSupporter(Integer supporterId){
        supporterRepository.deleteById(supporterId);
    }

    @Transactional
    public Supporter getSupporterByUsername(String username) {
        return supporterRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("Supporter with username " + username + " not found")));
    }
}
