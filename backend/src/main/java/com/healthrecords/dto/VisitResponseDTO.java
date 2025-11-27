package com.healthrecords.dto;

import java.time.LocalDateTime;

public class VisitResponseDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
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
    
    public VisitResponseDTO() {
    }
    
    public VisitResponseDTO(Long id, Long patientId, String patientName, Long doctorId, String doctorName, LocalDateTime visitDate, String reasonForVisit, String symptoms, String diagnosis, String prescribedMedicines, Double heightCm, Double weightKg, String bloodPressure, Integer pulse, Double temperature, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.visitDate = visitDate;
        this.reasonForVisit = reasonForVisit;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.prescribedMedicines = prescribedMedicines;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.bloodPressure = bloodPressure;
        this.pulse = pulse;
        this.temperature = temperature;
        this.notes = notes;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Long getPatientId() {
        return patientId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public Long getDoctorId() {
        return doctorId;
    }
    
    public String getDoctorName() {
        return doctorName;
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
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

