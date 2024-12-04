package com.example.SpirngSecEx.service;

import com.example.SpirngSecEx.model.Contribution;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.repository.ProjectRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepository;

    public ProjectService(ProjectRepo projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    @Transactional
    public void saveProject (Project project){
        projectRepository.save(project);
    }

    @Transactional
    public Project getProject(Integer projectId){
        return projectRepository.findById(projectId).get();
    }

    @Transactional
    public void updateAmount(Integer projectId, double amount){
        Project pro = projectRepository.findById(projectId).get();
        pro.addAmount(amount);
        projectRepository.save(pro);
    }

    @Transactional
    public void updateProjectSupporterList(Project project, Supporter supporter){
        project.addSupporter(supporter);
        projectRepository.save(project);
    }

    @Transactional
    public void updateProjectContributionList(Project project, Contribution contribution){
        project.addContribution(contribution);
        projectRepository.save(project);
    }
}
