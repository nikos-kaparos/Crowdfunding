package com.example.SpirngSecEx.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Creator extends Users{
    @OneToMany(mappedBy = "creator", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Project> projects;

    public Creator() {
        super();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setCreator(this);  // Set the creator of the project
    }


}
