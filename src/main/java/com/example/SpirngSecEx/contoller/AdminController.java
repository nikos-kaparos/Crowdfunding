package com.example.SpirngSecEx.contoller;
import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Project;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.model.Users;
import com.example.SpirngSecEx.service.CreatorService;
import com.example.SpirngSecEx.service.MyUserDetailsService;
import com.example.SpirngSecEx.service.ProjectService;
import com.example.SpirngSecEx.service.SupporterService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MyUserDetailsService myUserDetailsService;

    private final ProjectService projectService;

    private final CreatorService creatorService;

    private final SupporterService supporterService;

    @Autowired
    public AdminController(MyUserDetailsService myUserDetailsService , ProjectService projectService, CreatorService creatorService, SupporterService supporterService) {
        this.myUserDetailsService = myUserDetailsService;
        this.projectService = projectService;
        this.creatorService = creatorService;
        this.supporterService = supporterService;
    }

    @GetMapping("/users")
    @JsonView(Users.Public.class) // Επιστρέφει μόνο τα πεδία της Public view
    public List<Users> getUsers(Authentication authentication) {
        return myUserDetailsService.getAllUsers();
    }

    @PostMapping("users/{id}/enable")
    public ResponseEntity<String> enableUser(@PathVariable int id, @RequestParam boolean enabled, Authentication authentication) {
        try {
            Optional<Users> optionalUser = Optional.ofNullable(myUserDetailsService.findUser(id));
            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();
                user.setEnabled(enabled);
                myUserDetailsService.saveUser(user);
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.ok("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }


    @GetMapping("/project")
//    @JsonView(Project.Public.class)
    public List<Project> getProjects(Authentication authentication) {
        return projectService.getProjects();
    }


    @PostMapping("/project/{id}/status")
    public ResponseEntity<String> updateProject(@PathVariable int id, boolean status, Authentication authentication) {
        System.out.println("User: " + authentication.getName() + " is trying to activate project " + id);
        Optional<Project> optionalProject = Optional.ofNullable(projectService.getProject(id));
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setStatus(status);
            projectService.saveProject(project);
            return ResponseEntity.ok("Project status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id, Authentication authentication) {
        try {
            Optional<Users> optionalUser = Optional.ofNullable(myUserDetailsService.findUser(id));

            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();

                // Ελέγχουμε αν ο χρήστης συμμετέχει σε έργα (supporter ή creator)
                List<Project> projects = new ArrayList<>();
                if (user instanceof Supporter) {
                    projects = ((Supporter) user).getProjects(); // Αν ο χρήστης είναι υποστηρικτής
                } else if (user instanceof Creator) {
                    projects = ((Creator) user).getProjects(); // Αν ο χρήστης είναι δημιουργός
                }
                // Ελέγχουμε αν τα έργα πληρούν την προϋπόθεση για διαγραφή του χρήστη
                for (Project project : projects) {
                    if (project.getTotalFunding() != project.getRequiredFunding()) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body("Cannot delete user: Project funding is not complete.");
                    }
                }
                // Αν τα έργα πληρούν την προϋπόθεση, προχωράμε στη διαγραφή του χρήστη και των συναφών δεδομένων
                myUserDetailsService.deleteUser(id); // Κλήση της υπηρεσίας για διαγραφή χρήστη
                creatorService.deleteCreator(id);
                supporterService.deleteSupporter(id);

                return ResponseEntity.ok("User deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user.");
        }
    }

}

