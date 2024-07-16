package com.UserManagement.UserManagement.model;

public class SignupResponse {

    private String name;
    private String message;
    private Integer status;
    private Role role;



    public SignupResponse( String message, Integer status, Role role,String name) {

        this.message = message;
        this.status = status;
        this.role = role;
        this.name=name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
