package com.example.SpirngSecEx.contoller;

import com.example.SpirngSecEx.model.Contribution;
import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.service.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    private SupporterService supporterService;
    private ContributionService contributionService;
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private CreatorService creatorService;

    public ProjectController(ProjectService projectService, SupporterService supporterService, ContributionService contributionService) {
        this.projectService = projectService;
        this.supporterService = supporterService;
        this.contributionService = contributionService;
    }

    @GetMapping("")
    public String showProjects(Model model) {
        // Λήψη όλων των projects
        List<Project> allProject = projectService.getProjects();
        // Δημιουργία μιας νέας λίστας για τα ενεργά projects
        List<Project> enabledProjects = new ArrayList<>();
        // Φιλτράρισμα και προσθήκη των ενεργών projects στη λίστα
        for (Project project : allProject) {
            if (project.isStatus()) {
                enabledProjects.add(project);
            }
        }
        // Προσθήκη των ενεργών projects στο μοντέλο
        model.addAttribute("listProjects", enabledProjects);
        // Επιστροφή του view
        return "project/project";
    }


    @GetMapping("/new")
    public String addProject(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Παίρνουμε το όνομα χρήστη
        Project pro = new Project();
        model.addAttribute("username", username);
        model.addAttribute("emptyProject", pro);
        return "project/project_form";
    }

//    @PostMapping("/new")
//    public String saveProject(@ModelAttribute("pro")Project project, Model model) {
//        System.out.println("List of projects before: " + projectService.getProjects());
//        projectService.saveProject(project);
//        System.out.println("List of projects after: " + projectService.getProjects());
//        System.out.println("Created project: " + project.getCreator());
//        model.addAttribute("listProjects", projectService.getProjects());
//        return "project/project";
//    }
    @PostMapping("/new")
    public String saveProject(@ModelAttribute("pro") Project project, Model model) {
        // Ανάθεση του creator στο project πριν την αποθήκευση
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Παίρνουμε το όνομα χρήστη
        model.addAttribute("username", username);
        Creator creator = creatorService.findByUsername(username);
        // Εκτύπωση για να δούμε ποιος είναι ο δημιουργός του project
        project.setCreator(creator);
        System.out.println("Created project with creator: " + project.getCreator());
//      Αποθήκευση του project με τον δημιουργό του
        projectService.saveProject(project);
//      Προσθήκη της λίστας των projects στο model για να εμφανιστεί στο view
        model.addAttribute("listProjects", projectService.getProjects());
        return "project/project";
    }

    @GetMapping("/details/{id}")
    public String projectDetails(@PathVariable Integer id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Παίρνουμε το όνομα χρήστη
        model.addAttribute("creator", projectService.getProjectCreator(id));
        model.addAttribute("supList", supporterService.getSupporters());
        model.addAttribute("project", projectService.getProject(id));
        model.addAttribute("username", username);
        return "project/project_details";
    }



    @PostMapping("/support/{id}")
    public String supportProject(@PathVariable Integer id, @RequestParam("amount") double amount, @RequestParam("supId") Integer supId
            , Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Παίρνουμε το όνομα χρήστη

        //We bring the project and the supporter based on their IDs
        Project project = projectService.getProject(id);
        Supporter supporter = supporterService.getSupporter(supId);

        //I must make sure that the support cannot support the same project twice
        if (supporter.validateSupport(id)){
            System.out.println ("The supporter already supports this project");
            return "index";
        }
        //I create a new contribution with the amount, the supporter and the project, and saves it
        Contribution contribution = new Contribution(amount, project, supporter);
        contributionService.saveContribution(contribution);

        //Adding the amount of that the supporter provided to the total amount of the project
        projectService.updateAmount(id, amount);

        //Adding the contribution to the project_contribution list
        projectService.updateProjectContributionList(project, contribution);

        //Adding the contribution to the supporter_contribution list
        supporterService.updateSupporterContributionList(supporter, contribution);

        //Adding the project to the list of the supporter_projects
        supporterService.updateSupporterProjectList(project, supporter);

        //Adding the supporter to the list of the project_supporter
        projectService.updateProjectSupporterList(project, supporter);

        model.addAttribute("listProjects", projectService.getProjects());
        model.addAttribute("username", username);

        return "project/project";
    }

}
