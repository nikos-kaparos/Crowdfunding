package com.example.SpirngSecEx.service;

import com.example.SpirngSecEx.model.Contribution;
import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.repository.ContributionRepo;
import com.example.SpirngSecEx.repository.ProjectRepo;
import com.example.SpirngSecEx.repository.SupporterRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepository;
    @Autowired
    private ContributionRepo contributionRepo;
    @Autowired
    private SupporterRepo supporterRepo;
    @Autowired
    private ProjectRepo projectRepo;

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
    public Project getProject(Integer projectId) {
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

    @Transactional
    public void deleteProject(Integer projectId){
        Project project = projectRepo.findById(projectId).get();
        // Αφαίρεση του project από τη λίστα των projects του supporter
        for (Supporter supporter : project.getSupporters()) {
            supporter.getProjects().remove(project);
        }
        contributionRepo.deleteByProjectId(projectId);
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public Creator getProjectCreator(Integer projectId){
        Project project = projectRepository.findById(projectId).orElse(null);
        if(project == null){
            return project.getCreator();
        }
        return null;
    }
}
