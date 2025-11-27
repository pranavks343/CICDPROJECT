package com.healthrecords.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Override
    public void run(String... args) {
        // No default users - all users must register through the API
        System.out.println("Application started. Users can register through /api/users endpoint.");
    }
}

