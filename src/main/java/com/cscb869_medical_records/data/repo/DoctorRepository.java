package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByGpTrue();
}
