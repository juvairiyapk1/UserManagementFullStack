package com.UserManagement.UserManagement.controller;

import com.UserManagement.UserManagement.model.AuthenticationResponse;
import com.UserManagement.UserManagement.model.SignupResponse;
import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.repository.UserRepository;
import com.UserManagement.UserManagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody User request){

        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body("Username is already taken.");

        }
        SignupResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody User request){
        AuthenticationResponse response=authenticationService.authenticate(request);
        System.out.println(response);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
