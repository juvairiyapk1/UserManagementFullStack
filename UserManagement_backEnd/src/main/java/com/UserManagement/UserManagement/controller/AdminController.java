package com.UserManagement.UserManagement.controller;

import com.UserManagement.UserManagement.Dto.UserDto;
import com.UserManagement.UserManagement.model.SearchRequest;
import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin_only")
public class AdminController {


    @Autowired
    UserService userService;
    @GetMapping("/userList")
    public ResponseEntity<List<UserDto>> getUserList() {
        List<UserDto>userList=userService.findAllUsers();
        return ResponseEntity.ok(userList);
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> addProfile(@RequestBody User user) throws IOException {
        String savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }


    @PutMapping("/editProfile/{id}")
    public ResponseEntity<String> editUser(@PathVariable Integer id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok("User updated successfully!");
        } else {
            return ResponseEntity.ok("User update failed!");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody SearchRequest name) {
        List<UserDto> users = null;
        System.out.println(name.getName());
        users = userService.findByNameContaining(name.getName());
        System.out.println(users+"users");
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully!");
    }


}
