package com.example.SpirngSecEx.contoller;

import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.model.Users;
import com.example.SpirngSecEx.service.CreatorService;
import com.example.SpirngSecEx.service.MyUserDetailsService;
import com.example.SpirngSecEx.service.SupporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private SupporterService supporterService;
    @Autowired
    private CreatorService creatorService;

    @GetMapping("/register")
    public String showRegister(Model model){
        System.out.println("register");
        //model.addAttribute("title","register");
        model.addAttribute("user", new Users());
        return "signup/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user,
                               @RequestParam("role") String role, Model model) {
        if (userDetailsService.existByUser(user.getUsername())) {
            model.addAttribute("error", "This username is already taken");
            System.out.println("Error, this username is already taken");
            return "signup/register";
        }
        if ("supporter".equals(role)) {
            Supporter supporter = new Supporter();
            supporter.setUsername(user.getUsername());
            supporter.setPassword(user.getPassword());
            supporter.setEmail(user.getEmail());
            supporter.setRole(role);
            supporterService.saveSupporter(supporter);
            System.out.println("DONE " + supporter.getUsername() + " saved");
        }else {
            Creator creator = new Creator();
            creator.setUsername(user.getUsername());
            creator.setPassword(user.getPassword());
            creator.setEmail(user.getEmail());
            creator.setRole(role);
            creatorService.saveCreator(creator);
            System.out.println("DONE " + creator.getUsername() + " saved");
        }
        return "signup/registrationWait";
    }

    @GetMapping("/registrationWait")
    public String showRegistrationWait(Model model){
        return "singup/registrationWait";
    }
}
