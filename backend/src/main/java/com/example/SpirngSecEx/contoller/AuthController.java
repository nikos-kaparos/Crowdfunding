package com.example.SpirngSecEx.contoller;
import com.example.SpirngSecEx.configuration.JwtUtils;
import com.example.SpirngSecEx.model.Creator;
import com.example.SpirngSecEx.model.Supporter;
import com.example.SpirngSecEx.model.Users;
import com.example.SpirngSecEx.payload.request.LoginRequest;
import com.example.SpirngSecEx.payload.request.SignupRequest;
import com.example.SpirngSecEx.payload.respone.JwtResponse;
import com.example.SpirngSecEx.payload.respone.MessageResponse;
import com.example.SpirngSecEx.repository.UserRepo;
import com.example.SpirngSecEx.service.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    UserRepo userRepository;
    BCryptPasswordEncoder encoder;
    JwtUtils jwtUtils;
    MyUserDetailsService userDetailsService;

    public AuthController( AuthenticationManager authenticationManager, UserRepo userRepository, BCryptPasswordEncoder encoder, JwtUtils jwtUtils, MyUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(
                new MessageResponse("Logged out successfully")
        );
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if (!userDetailsService.existByUser(loginRequest.getUsername())) {
            System.out.println("User not found");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is not exist!"));
        }
        Users user = userRepository.findByUsername(loginRequest.getUsername());

        if (!user.isEnabled()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User is not enabled!"));
        }

        System.out.println("authentication");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println("authentication: " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("post authentication");
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jwt: " + jwt);

        System.out.println("userID: " + user.getId() + " USERNAME: " + user.getUsername() + " ROLE: " + user.getRole());
        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()));

    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        System.out.println("authentication");
//        System.out.println(loginRequest.getUsername());
//        System.out.println(loginRequest.getPassword());
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        System.out.println("authentication" + authentication);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("post authentication");
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        System.out.println("jwt" + jwt);
//        userDetailsService.loadUserByUsername(loginRequest.getUsername());
//        Users user = new Users(loginRequest.getUsername());
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                user.getId(),
//                user.getUsername(),
//                user.getRole()));
//    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
////      Verify user
//        System.out.println("authentication");
////        System.out.println(user.getUsername());
////        System.out.println(user.getPassword());
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("post authentication");
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        System.out.println("jwt " + jwt);
//        Users user = new Users(loginRequest.getUsername());
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                user.getId(),
//                user.getUsername(),
//                user.getRole()));
//    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        Users user = new Users(signUpRequest.getUsername());
        //Adding user to creator table with user.id
        if (signUpRequest.getRole().equals("creator")){
            user =  new Creator();
        }else if (signUpRequest.getRole().equals("supporter")){
            user =  new Supporter();
        }
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRole(signUpRequest.getRole());
        user.setEmail(signUpRequest.getEmail());
        if (user.getRole().equals("admin")) {
            user.setEnabled(true);
        }
        userDetailsService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

