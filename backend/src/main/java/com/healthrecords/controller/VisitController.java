package com.healthrecords.controller;

import com.healthrecords.dto.VisitRequestDTO;
import com.healthrecords.dto.VisitResponseDTO;
import com.healthrecords.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {
    
    private final VisitService visitService;
    
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }
    
    @PostMapping
    public ResponseEntity<VisitResponseDTO> createVisit(@Valid @RequestBody VisitRequestDTO visitRequest) {
        VisitResponseDTO createdVisit = visitService.createVisit(visitRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisit);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VisitResponseDTO> getVisitById(@PathVariable Long id) {
        VisitResponseDTO visit = visitService.getVisitById(id);
        return ResponseEntity.ok(visit);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitResponseDTO>> getVisitsByPatientId(@PathVariable Long patientId) {
        List<VisitResponseDTO> visits = visitService.getVisitsByPatientId(patientId);
        return ResponseEntity.ok(visits);
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<VisitResponseDTO>> getVisitsByDoctorId(@PathVariable Long doctorId) {
        List<VisitResponseDTO> visits = visitService.getVisitsByDoctorId(doctorId);
        return ResponseEntity.ok(visits);
    }
    
    @GetMapping
    public ResponseEntity<List<VisitResponseDTO>> getAllVisits() {
        List<VisitResponseDTO> visits = visitService.getAllVisits();
        return ResponseEntity.ok(visits);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }
}

