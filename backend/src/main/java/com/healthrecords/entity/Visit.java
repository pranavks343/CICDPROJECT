package com.healthrecords.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
public class Visit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    @Column(nullable = false)
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
    
    @Column(length = 2000)
    private String notes;
    
    public Visit() {
    }
    
    public Visit(Long id, User patient, User doctor, LocalDateTime visitDate, String reasonForVisit, String symptoms, String diagnosis, String prescribedMedicines, Double heightCm, Double weightKg, String bloodPressure, Integer pulse, Double temperature, String notes) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
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
    
    public User getPatient() {
        return patient;
    }
    
    public User getDoctor() {
        return doctor;
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
    
    public void setPatient(User patient) {
        this.patient = patient;
    }
    
    public void setDoctor(User doctor) {
        this.doctor = doctor;
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

