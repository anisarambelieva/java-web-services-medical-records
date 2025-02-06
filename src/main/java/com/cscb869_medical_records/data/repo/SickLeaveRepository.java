package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Month;
import java.util.List;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
    List<SickLeave> findByPatientId(Long patientId);

    @Query("SELECT s.doctor.name, COUNT(s) FROM SickLeave s GROUP BY s.doctor ORDER BY COUNT(s) DESC")
    List<Object[]> findTopDoctorBySickLeaves();

    @Query("SELECT FUNCTION('MONTHNAME', s.startDate), COUNT(s) FROM SickLeave s GROUP BY FUNCTION('MONTHNAME', s.startDate) ORDER BY COUNT(s) DESC")
    List<Object[]> findTopMonthBySickLeaves();
}
