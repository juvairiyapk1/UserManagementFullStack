package com.UserManagement.UserManagement.controller;

import com.UserManagement.UserManagement.Dto.UserDto;
import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.repository.UserRepository;
import com.UserManagement.UserManagement.service.JwtService;
import com.UserManagement.UserManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepo;



//    @GetMapping("profile/{email}")
//    public ResponseEntity<UserDto> getUserByUserName(@PathVariable String email) {
//        UserDto user = userService.findUserByUserName(email);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);
//    }
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getprofile(@RequestHeader("Authorization") String authorizationHeader){
        String token=authorizationHeader.substring(7);
        String username=jwtService.extractUserName(token);
        UserDto user=userService.findUserByUserName(username);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<?> updateUserImage(@RequestParam("file") MultipartFile imgfile,@RequestParam("username")String username){
        try {
            String imagepath=imgfile.getOriginalFilename();
            File filestore = new File("src/main/resources/static/img/");
            if (!filestore.exists()) {
                filestore.mkdirs();
            }
            Path path = Paths.get(filestore.getAbsolutePath() + File.separator + imagepath);
            Files.copy(imgfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            User user=userRepo.findByEmail(username);
            user.setImg(imagepath);
            userRepo.save(user);
            return ResponseEntity.ok("profile photo updated successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }



    @PutMapping("/profileEdit")
    public ResponseEntity<String> editProfile(@RequestParam String user, @RequestPart("img") MultipartFile img) throws IOException {
        System.out.println(user);
        System.out.println(img);
//        userService.updateProfile(user.getEmail(),user, img);
        return ResponseEntity.ok("Edit success");
    }


    @GetMapping()
    public ResponseEntity<?> checkUserAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null && authentication.isAuthenticated()) {
            // The user is authenticated, return a successful response
            return ResponseEntity.ok().build();
        } else {
            // The user is not authenticated, return an error response
            return ResponseEntity.status(401).body("User is not authenticated");
        }
    }

}
