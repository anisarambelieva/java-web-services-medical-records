package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//    int findPatientsCountByDoctor(Doctor doctor);
//    int findExamsCountByDoctor(Doctor doctor);

}
