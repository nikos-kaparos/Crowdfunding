package com.example.SpirngSecEx.contoller;
import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.service.CreatorService;
import com.example.SpirngSecEx.service.ProjectService;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/creator")
public class CreatorController {

    private CreatorService creatorService;
    private ProjectService projectService;

    public CreatorController(CreatorService creatorService, ProjectService projectService) {
        this.creatorService = creatorService;
        this.projectService = projectService;
    }

    @GetMapping("/my-projects")
    public String viewMyProjects(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Creator creator = creatorService.findByUsername(username);
        System.out.println("the creator name is " + creator);
        model.addAttribute("projects", creator.getProjects());
        model.addAttribute("username", creator.getUsername());
        return "creator/my_projects";
    }

    // **5. Επεξεργασία έργου**
    @GetMapping("/edit-project/{id}")
    public String editProjectForm(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Creator creator = creatorService.findByUsername(username);
        if (creator == null) {
            return "redirect:/login";
        }
        System.out.println("the creator name is " + creator.getUsername() + "with id" + creator.getId());
        Project project = projectService.getProject(id);
        System.out.println(project.getId());
        model.addAttribute("project", project);
        model.addAttribute("username", creator.getUsername());
//        model.addAttribute("creator", creator);
        return "creator/edit_project_form";
    }

    @PostMapping("/edit-project/{id}")
    public String updateProject(@PathVariable("id") int id, @ModelAttribute("project") Project updatedProject) {
        Project existingProject = projectService.getProject(id);
        System.out.println("Project title is " + existingProject.getTitle() + "Project id is " + existingProject.getId());
        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setRequiredFunding(updatedProject.getRequiredFunding());
        projectService.saveProject(existingProject);
        return "redirect:/creator/my-projects";
    }

    // **6. Διαγραφή έργου**
    @GetMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable("id") Integer id) {
        projectService.deleteProject(id);
        return "redirect:/creator/my-projects";
    }


}
