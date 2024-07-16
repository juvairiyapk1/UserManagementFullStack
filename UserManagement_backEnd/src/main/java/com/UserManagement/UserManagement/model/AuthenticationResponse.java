package com.UserManagement.UserManagement.model;

public class AuthenticationResponse {
    private String token;
    private String name;
    private String email;

    private Role role;


    public AuthenticationResponse(String token, String name, String email, Role role) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return name;
    }

    public String getUserName() {
        return email;
    }



    public String getToken() {
        return token;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", firstName='" + name + '\'' +
                ", userName='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
