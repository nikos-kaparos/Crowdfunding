package com.example.SpirngSecEx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Supporter extends Users{


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(
            name = "supporter_project",
            joinColumns = @JoinColumn(name = "supporter_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"supporter_id", "project_id"})
    )
//    @JsonIgnore
//    @JsonManagedReference
    private List<Project> projects;


    @OneToMany(mappedBy = "supporter")
    private List<Contribution> contributions;


    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project){
        projects.add(project);
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public void addContributionToSupporter(Contribution contribution){
        contributions.add(contribution);
    }

    public boolean validateSupport (Integer id){

        for (Project project : this.projects) {
            if (project.getId().equals(id))
                return true;
        }

        return false;
    }

}
