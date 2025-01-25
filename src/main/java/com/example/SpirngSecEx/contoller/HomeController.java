package com.example.SpirngSecEx.contoller;

import com.example.SpirngSecEx.service.MyUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    private final MyUserDetailsService myUserDetailsService;
    public HomeController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping
    public String home(Authentication authentication) {
        String username = authentication.getName();
        return "Hello World " + username;
    }
}

//@Controller
//public class HomeController {
//
//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("title","home");
//        return "signup/register";
//    }
//
//    @GetMapping("/admin")
//    public String adminPanel(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        System.out.println("User authorities" + authorities);
//        model.addAttribute("title", "admin");
//        return "adminPage";
//    }
//
//    @GetMapping("/donor")
//    public String systemPanel(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        System.out.println("User authorities" + authorities);
//        model.addAttribute("title", "system");
//        return "systemPage";
//    }
//}




