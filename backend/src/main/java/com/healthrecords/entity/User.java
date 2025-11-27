package com.healthrecords.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    @Column(unique = true)
    private String phoneNumber;
    
    private String gender;
    
    private LocalDate dateOfBirth;
    
    // Only for doctors
    private String specialization;
    
    private String address;
    
    public User() {
    }
    
    public User(Long id, String fullName, String email, String password, Role role, String phoneNumber, String gender, LocalDate dateOfBirth, String specialization, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
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
    
    public String getPassword() {
        return password;
    }
    
    public Role getRole() {
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
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRole(Role role) {
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
    
    public enum Role {
        ADMIN, DOCTOR, PATIENT
    }
}

