package com.UserManagement.UserManagement.service;

import com.UserManagement.UserManagement.Dto.UserDto;
import com.UserManagement.UserManagement.model.EditRequest;
import com.UserManagement.UserManagement.model.Role;
import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public UserDto findUserByUserName(String email) {
        User user= userRepository.findByEmail(email);
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setEmail(user.getEmail());
                    userDto.setName(user.getName());
                    userDto.setRole(user.getRole());
                    return userDto;
                })
                .collect(Collectors.toList());
    }
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id,User user) {

        User user1=userRepository.findById(id).get();
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());

        return userRepository.save(user1);
    }




    public String addUser(User request) {
        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.valueOf("USER"));

        user=userRepository.save(user);
        return "redirect:/userList";
    }

    public List<UserDto> findByNameContaining(String name) {
     List<User> users=userRepository.findByNameContainingIgnoreCase(name);
     List<UserDto>ans=new ArrayList<>();
     for (User u:users){
         UserDto user=new UserDto();
         user.setId(u.getId());
         user.setName(u.getName());
         user.setEmail(u.getEmail());
         user.setRole(u.getRole());
         ans.add(user);
     }
        System.out.println(users+"hello");
        return ans;
    }

    public User updateProfile(String email,  MultipartFile img) throws IOException {
        User userToUpdate=userRepository.findByEmail(email);
//        userToUpdate.setName(user.getName());
        if(img !=null){
            String imgPath=uploadImg(img);
            userToUpdate.setImg(imgPath);
        }
       return userRepository.save(userToUpdate);
    }

    private String uploadImg(MultipartFile img) throws IOException {
        String imgPath=img.getOriginalFilename();
        File imgStore=new File("src/main/resources/static/img/");
        if (!imgStore.exists()) {
            imgStore.mkdirs();
        }
        Path path= Paths.get(imgStore.getAbsolutePath()+File.separator+imgPath);
        Files.copy(img.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
        return imgPath;
    }
}
