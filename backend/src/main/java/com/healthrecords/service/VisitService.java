package com.healthrecords.service;

import com.healthrecords.dto.VisitRequestDTO;
import com.healthrecords.dto.VisitResponseDTO;
import com.healthrecords.entity.User;
import com.healthrecords.entity.Visit;
import com.healthrecords.exception.ResourceNotFoundException;
import com.healthrecords.repository.UserRepository;
import com.healthrecords.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {
    
    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    
    public VisitService(VisitRepository visitRepository, UserRepository userRepository) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
    }
    
    public VisitResponseDTO createVisit(VisitRequestDTO visitRequest) {
        User patient = userRepository.findById(visitRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + visitRequest.getPatientId()));
        
        User doctor = userRepository.findById(visitRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + visitRequest.getDoctorId()));
        
        // Check if a visit with the same patient, doctor, and date already exists
        if (visitRepository.findByPatientIdAndDoctorIdAndVisitDate(
                visitRequest.getPatientId(), 
                visitRequest.getDoctorId(), 
                visitRequest.getVisitDate()).isPresent()) {
            throw new com.healthrecords.exception.DuplicateResourceException(
                "A visit already exists for this patient with this doctor at the same date and time.");
        }
        
        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setVisitDate(visitRequest.getVisitDate());
        visit.setReasonForVisit(visitRequest.getReasonForVisit());
        visit.setSymptoms(visitRequest.getSymptoms());
        visit.setDiagnosis(visitRequest.getDiagnosis());
        visit.setPrescribedMedicines(visitRequest.getPrescribedMedicines());
        visit.setHeightCm(visitRequest.getHeightCm());
        visit.setWeightKg(visitRequest.getWeightKg());
        visit.setBloodPressure(visitRequest.getBloodPressure());
        visit.setPulse(visitRequest.getPulse());
        visit.setTemperature(visitRequest.getTemperature());
        visit.setNotes(visitRequest.getNotes());
        
        Visit savedVisit = visitRepository.save(visit);
        return convertToDTO(savedVisit);
    }
    
    public VisitResponseDTO getVisitById(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        return convertToDTO(visit);
    }
    
    public List<VisitResponseDTO> getVisitsByPatientId(Long patientId) {
        return visitRepository.findByPatientId(patientId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<VisitResponseDTO> getVisitsByDoctorId(Long doctorId) {
        return visitRepository.findByDoctorId(doctorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<VisitResponseDTO> getAllVisits() {
        return visitRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Visit not found with id: " + id);
        }
        visitRepository.deleteById(id);
    }
    
    private VisitResponseDTO convertToDTO(Visit visit) {
        return new VisitResponseDTO(
                visit.getId(),
                visit.getPatient().getId(),
                visit.getPatient().getFullName(),
                visit.getDoctor().getId(),
                visit.getDoctor().getFullName(),
                visit.getVisitDate(),
                visit.getReasonForVisit(),
                visit.getSymptoms(),
                visit.getDiagnosis(),
                visit.getPrescribedMedicines(),
                visit.getHeightCm(),
                visit.getWeightKg(),
                visit.getBloodPressure(),
                visit.getPulse(),
                visit.getTemperature(),
                visit.getNotes()
        );
    }
}

