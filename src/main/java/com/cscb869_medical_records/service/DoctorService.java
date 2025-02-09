package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.dto.doctor.CreateDoctorDTO;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    List<DoctorDTO> getDoctors();
    DoctorDTO getDoctor(long id);
    CreateDoctorDTO createDoctor(CreateDoctorDTO doctor);
    UpdateDoctorDTO updateDoctor(UpdateDoctorDTO doctor, long id);
    void deleteDoctor(long id);
    Doctor findDoctorWithMostSickLeaves();
    Map<Long, Integer> getNumberOfExamsPerDoctor();
    List<ExamDTO> getExamsByDoctor(long doctorId);
    List<DoctorDTO> getGpDoctors();
}
