package com.healthrecords.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class VisitRequestDTO {
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    
    @NotNull(message = "Visit date is required")
    private LocalDateTime visitDate;
    
    private String reasonForVisit;
    
    private String symptoms;
    
    private String diagnosis;
    
    private String prescribedMedicines;
    
    private Double heightCm;
    
    private Double weightKg;
    
    private String bloodPressure;
    
    private Integer pulse;
    
    private Double temperature;
    
    private String notes;
    
    // Getters
    public Long getPatientId() {
        return patientId;
    }
    
    public Long getDoctorId() {
        return doctorId;
    }
    
    public LocalDateTime getVisitDate() {
        return visitDate;
    }
    
    public String getReasonForVisit() {
        return reasonForVisit;
    }
    
    public String getSymptoms() {
        return symptoms;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public String getPrescribedMedicines() {
        return prescribedMedicines;
    }
    
    public Double getHeightCm() {
        return heightCm;
    }
    
    public Double getWeightKg() {
        return weightKg;
    }
    
    public String getBloodPressure() {
        return bloodPressure;
    }
    
    public Integer getPulse() {
        return pulse;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public String getNotes() {
        return notes;
    }
    
    // Setters
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    
    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }
    
    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }
    
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public void setPrescribedMedicines(String prescribedMedicines) {
        this.prescribedMedicines = prescribedMedicines;
    }
    
    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }
    
    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }
    
    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
    
    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}

