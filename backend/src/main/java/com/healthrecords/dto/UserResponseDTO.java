package com.healthrecords.dto;

import com.healthrecords.entity.User;

import java.time.LocalDate;

public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private User.Role role;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private String specialization;
    private String address;
    
    public UserResponseDTO() {
    }
    
    public UserResponseDTO(Long id, String fullName, String email, User.Role role, String phoneNumber, String gender, LocalDate dateOfBirth, String specialization, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.specialization = specialization;
        this.address = address;
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
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getGender() {
        return gender;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public String getAddress() {
        return address;
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
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}

