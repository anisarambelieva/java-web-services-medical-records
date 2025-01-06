package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Списък с пациенти, с дадена диагноза.
    List<Patient> findAllByDiagnosisNameContains(String diagnosisName);

    // Списък с пациенти, които имат даден личен лекар.
    List<Patient> findAllByDoctorId(long doctorId);
}