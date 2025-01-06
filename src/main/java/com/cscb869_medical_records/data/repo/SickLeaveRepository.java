package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
//    Month findMonthWithMostSickLeaves();

//    Doctor findDoctorWithMostSickLeaves();
}
