package com.example.SpirngSecEx.contoller;


import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.service.SupporterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supporters")
public class SupporterController {

    private final SupporterService supporterService;

    public SupporterController(SupporterService supporterService) {
        this.supporterService = supporterService;
    }

    @GetMapping("")
    public ResponseEntity<List<Supporter>> getAllSupporters() {
        List<Supporter> supporters = supporterService.getSupporters();
        return ResponseEntity.ok(supporters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supporter> getSupporterDetails(@PathVariable("id") Integer id) {
        Supporter supporter = supporterService.getSupporter(id);
        if (supporter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supporter);
    }
}
