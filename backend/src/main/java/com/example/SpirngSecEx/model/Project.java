package com.example.SpirngSecEx.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.springframework.scheduling.config.Task;

import java.util.List;

@Entity
public class Project {
//    public static class Public {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
//    @JsonView(Project.Public.class)
    private Integer id;

    @Column
//    @JsonView(Project.Public.class)
    private String title;

    @Column (columnDefinition = "TEXT")
//    @JsonView(Project.Public.class)
    private String description;

    @Column
//    @JsonView(Project.Public.class)
    private double requiredFunding;

    @Column
//    @JsonView(Project.Public.class)
    private double totalFunding;

    @Column
//    @JsonView(Project.Public.class)
    private boolean status;
    //True if the project is approved by the admin, false if the project has yet to be approved

    @ManyToMany(mappedBy = "projects", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonBackReference // Αυτή είναι η "αντίστροφη" πλευρά της σχέσης
    private List<Supporter> supporters;

    @OneToMany(mappedBy = "project")
    private List<Contribution> contributions;

    @ManyToOne
    @JoinColumn(name = "creator_id")
//    @JsonView(Project.Public.class)
    private Creator creator;

    public Project(Integer id ,String title, String description, double requiredFunding) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requiredFunding = requiredFunding;
        this.totalFunding = 0;
//        this.creator = creator;
    }

    public Project (String title, String description, double requiredFunding){
        this.description = description;
        this.title = title;
        this.requiredFunding = requiredFunding;
        this.totalFunding = 0;
    }


    public Project(){
        this.status = false;
        this.totalFunding = 0;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRequiredFunding() {
        return requiredFunding;
    }

    public void setRequiredFunding(double requiredFunding) {
        this.requiredFunding = requiredFunding;
    }

    public double getTotalFunding() {
        return totalFunding;
    }

    public void setTotalFunding(double totalFunding) {
        this.totalFunding = totalFunding;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Supporter> getSupporters() {
        return supporters;
    }

    public void setSupporters(List<Supporter> supporters) {
        this.supporters = supporters;
    }

    public void addAmount(double amount){
        if (this.totalFunding <= this.requiredFunding){
            if ((this.requiredFunding - this.totalFunding) >= amount){
                this.totalFunding = this.totalFunding + amount;
            }
        }
    }

    public void addSupporter (Supporter sup){
        supporters.add(sup);
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public void addContribution (Contribution contribution){
        contributions.add(contribution);
    }



    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", requiredFunding=" + requiredFunding +
                ", totalFunding=" + totalFunding +
                ", status=" + status +
                '}';
    }
}