package com.healthrecords.service;

import com.healthrecords.dto.UserRequestDTO;
import com.healthrecords.dto.UserResponseDTO;
import com.healthrecords.entity.User;
import com.healthrecords.exception.ResourceNotFoundException;
import com.healthrecords.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        // Check if email already exists
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new com.healthrecords.exception.DuplicateResourceException("Email already registered. Please use a different email.");
        }
        
        // Check if phone number already exists (if provided)
        if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
            if (userRepository.findByPhoneNumber(userRequest.getPhoneNumber()).isPresent()) {
                throw new com.healthrecords.exception.DuplicateResourceException("Phone number already registered. Please use a different phone number.");
            }
        }
        
        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // Plain password for simplicity
        user.setRole(User.Role.valueOf(userRequest.getRole().toUpperCase()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setGender(userRequest.getGender());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setSpecialization(userRequest.getSpecialization());
        user.setAddress(userRequest.getAddress());
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<UserResponseDTO> getUsersByRole(String role) {
        User.Role userRole = User.Role.valueOf(role.toUpperCase());
        return userRepository.findByRole(userRole).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }
    
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (!user.getEmail().equals(userRequest.getEmail()) &&
            userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new com.healthrecords.exception.DuplicateResourceException("Email already registered. Please use a different email.");
        }
        
        // Check if phone number is being changed and if it already exists
        if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty() &&
            !userRequest.getPhoneNumber().equals(user.getPhoneNumber()) &&
            userRepository.findByPhoneNumber(userRequest.getPhoneNumber()).isPresent()) {
            throw new com.healthrecords.exception.DuplicateResourceException("Phone number already registered. Please use a different phone number.");
        }
        
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(userRequest.getPassword());
        }
        user.setRole(User.Role.valueOf(userRequest.getRole().toUpperCase()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setGender(userRequest.getGender());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setSpecialization(userRequest.getSpecialization());
        user.setAddress(userRequest.getAddress());
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getSpecialization(),
                user.getAddress()
        );
    }
}

