package com.UserManagement.UserManagement.service;

import com.UserManagement.UserManagement.model.AuthenticationResponse;
import com.UserManagement.UserManagement.model.Role;
import com.UserManagement.UserManagement.model.SignupResponse;
import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public SignupResponse register(User request){
        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.valueOf("USER"));

        user=userRepository.save(user);


//        String token= jwtService.generateToken(user);
        SignupResponse sd=new SignupResponse( "Signup successful",200,user.getRole(),user.getName());
        System.out.println(sd);
        return new SignupResponse( "Signup successful",200,user.getRole(),user.getName());

    }

    public AuthenticationResponse authenticate(User request){
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()
               )
       );
       User user=userRepository.findByEmail(request.getEmail());

       String token=jwtService.generateToken(user);
       return new AuthenticationResponse(token, user.getName(), user.getEmail(),user.getRole() );
    }


}
