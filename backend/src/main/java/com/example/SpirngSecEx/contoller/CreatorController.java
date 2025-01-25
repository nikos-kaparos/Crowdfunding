package com.example.SpirngSecEx.contoller;
//import com.example.SpirngSecEx.model.Creator;
//import com.example.SpirngSecEx.model.Project;
//import com.example.SpirngSecEx.service.CreatorService;
//import com.example.SpirngSecEx.service.ProjectService;
//import org.hibernate.Hibernate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import jakarta.servlet.http.HttpSession;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/creator")
//public class CreatorController {
//
//    private CreatorService creatorService;
//    private ProjectService projectService;
//
//    public CreatorController(CreatorService creatorService, ProjectService projectService) {
//        this.creatorService = creatorService;
//        this.projectService = projectService;
//    }
//
//    @GetMapping("/my-projects")
//    public String viewMyProjects(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Creator creator = creatorService.findByUsername(username);
//        System.out.println("the creator name is " + creator);
//        model.addAttribute("projects", creator.getProjects());
//        model.addAttribute("username", creator.getUsername());
//        return "creator/my_projects";
//    }
//
//    // **5. Επεξεργασία έργου**
//    @GetMapping("/edit-project/{id}")
//    public String editProjectForm(@PathVariable("id") int id, Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Creator creator = creatorService.findByUsername(username);
//        if (creator == null) {
//            return "redirect:/login";
//        }
//        System.out.println("the creator name is " + creator.getUsername() + "with id" + creator.getId());
//        Project project = projectService.getProject(id);
//        System.out.println(project.getId());
//        model.addAttribute("project", project);
//        model.addAttribute("username", creator.getUsername());
////        model.addAttribute("creator", creator);
//        return "creator/edit_project_form";
//    }
//
//    @PostMapping("/edit-project/{id}")
//    public String updateProject(@PathVariable("id") int id, @ModelAttribute("project") Project updatedProject) {
//        Project existingProject = projectService.getProject(id);
//        System.out.println("Project title is " + existingProject.getTitle() + "Project id is " + existingProject.getId());
//        existingProject.setTitle(updatedProject.getTitle());
//        existingProject.setDescription(updatedProject.getDescription());
//        existingProject.setRequiredFunding(updatedProject.getRequiredFunding());
//        projectService.saveProject(existingProject);
//        return "redirect:/creator/my-projects";
//    }
//
//
//}

import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.service.CreatorService;
import com.example.SpirngSecEx.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creator")
public class CreatorController {

    @Autowired
    private CreatorService creatorService;

    private ProjectService projectService;

    public CreatorController(CreatorService creatorService, ProjectService projectService) {
        this.creatorService = creatorService;
        this.projectService = projectService;
    }

    @GetMapping("/my-projects")
    public ResponseEntity<List<Project>> viewMyProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Creator creator = creatorService.findByUsername(username);
        if (creator == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(creator.getProjects());
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Project deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting project: " + e.getMessage());
        }
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Creator creator = creatorService.findByUsername(username);
        // Αν δεν υπάρχει ο χρήστης, επιστρέφουμε σφάλμα (unauthorized)
        if (creator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        // Εύρεση του project με το id
        Project project = projectService.getProject(id);
        // Αν δεν υπάρχει το project, επιστρέφουμε σφάλμα (not found)
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Επιστρέφουμε το project και τα δεδομένα του δημιουργού (creator) ως JSON
        return ResponseEntity.ok(project);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") Integer id, @RequestBody Project updatedProject, Authentication authentication) {
        try {
            // Get the existing project
            Project existingProject = projectService.getProject(id);
            if (existingProject == null) {
                return ResponseEntity.status(404).body("Project not found");
            }
            // Update the fields of the existing project
            existingProject.setTitle(updatedProject.getTitle());
            existingProject.setDescription(updatedProject.getDescription());
            existingProject.setRequiredFunding(updatedProject.getRequiredFunding());
            // Save the updated project
            projectService.saveProject(existingProject);
            return ResponseEntity.ok("Project updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating project: " + e.getMessage());
        }
    }
}

