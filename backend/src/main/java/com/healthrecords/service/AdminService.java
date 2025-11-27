package com.healthrecords.service;

import com.healthrecords.entity.User;
import com.healthrecords.repository.UserRepository;
import com.healthrecords.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {
    
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    
    public AdminService(UserRepository userRepository, VisitRepository visitRepository) {
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
    }
    
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalDoctors", userRepository.countByRole(User.Role.DOCTOR));
        stats.put("totalPatients", userRepository.countByRole(User.Role.PATIENT));
        stats.put("totalVisits", visitRepository.count());
        return stats;
    }
}

