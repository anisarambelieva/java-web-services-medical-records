package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
