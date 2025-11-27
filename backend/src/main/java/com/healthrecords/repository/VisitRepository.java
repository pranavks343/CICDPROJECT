package com.healthrecords.repository;

import com.healthrecords.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatientId(Long patientId);
    List<Visit> findByDoctorId(Long doctorId);
    Optional<Visit> findByPatientIdAndDoctorIdAndVisitDate(Long patientId, Long doctorId, LocalDateTime visitDate);
}

