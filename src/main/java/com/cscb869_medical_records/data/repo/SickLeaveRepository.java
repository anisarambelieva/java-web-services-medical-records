package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
    List<SickLeave> findByPatientId(Long patientId);
}
