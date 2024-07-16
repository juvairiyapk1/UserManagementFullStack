package com.UserManagement.UserManagement.Dto;

import com.UserManagement.UserManagement.model.Role;

public class UserDto {
    private Integer id;
    private String name;
    private  String email;
    private Role role;


//    public String getLastName() {
//        return lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
//    }

//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }


}
