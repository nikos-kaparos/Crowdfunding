package com.example.SpirngSecEx.payload.respone;

import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.model.Supporter;

public class SupporterResponse {

    private String message;
    private Project project;
    private Supporter supporter;

    public SupporterResponse(String message, Project project, Supporter supporter) {
        this.message = message;
        this.project = project;
        this.supporter = supporter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Supporter getSupporter() {
        return supporter;
    }

    public void setSupporter(Supporter supporter) {
        this.supporter = supporter;
    }

}
