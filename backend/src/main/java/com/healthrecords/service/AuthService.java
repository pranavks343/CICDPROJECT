package com.healthrecords.service;

import com.healthrecords.dto.LoginRequestDTO;
import com.healthrecords.dto.LoginResponseDTO;
import com.healthrecords.entity.User;
import com.healthrecords.exception.ResourceNotFoundException;
import com.healthrecords.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
        
        // Simple password check (plain text comparison for simplicity)
        // In production, use password encoding
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        
        return new LoginResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

