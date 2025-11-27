package com.healthrecords.dto;

import com.healthrecords.entity.User;

public class LoginResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private User.Role role;
    
    public LoginResponseDTO() {
    }
    
    public LoginResponseDTO(Long id, String fullName, String email, User.Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public User.Role getRole() {
        return role;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setRole(User.Role role) {
        this.role = role;
    }
}

