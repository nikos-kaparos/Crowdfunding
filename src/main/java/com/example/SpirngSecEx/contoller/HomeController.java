package com.example.SpirngSecEx.contoller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//@RestController
//public class HomeController {
//
//    @GetMapping("/")
//    public String HomeController() {
//        //return "hello world";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        System.out.println("User authorities" + authorities);
//
//        if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
//            return "You are an admin";
//        }else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("SYSTEM"))) {
//            return "You are system";
//        }else
//            return "Welcome";
//    }
//}

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title","home");
        return "signup/register";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("User authorities" + authorities);
        model.addAttribute("title", "admin");
        return "adminPage";
    }

    @GetMapping("/donor")
    public String systemPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("User authorities" + authorities);
        model.addAttribute("title", "system");
        return "systemPage";
    }
}




