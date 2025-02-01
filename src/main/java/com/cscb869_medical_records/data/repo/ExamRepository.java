package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Exam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Exam e SET e.diagnosis = NULL WHERE e.diagnosis.id = :diagnosisId")
    void updateDiagnosisToNull(@Param("diagnosisId") Long diagnosisId);

    List<Exam> findByPatientId(Long patientId);
}
