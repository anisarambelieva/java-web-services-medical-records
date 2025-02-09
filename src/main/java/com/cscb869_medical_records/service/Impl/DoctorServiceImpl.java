package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.DoctorRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.dto.doctor.CreateDoctorDTO;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.service.DoctorService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ExamRepository examRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<DoctorDTO> getDoctors() {
        return this.mapperUtil.mapList(this.doctorRepository.findAll(), DoctorDTO.class);
    }

    @Override
    public DoctorDTO getDoctor(long id) {
        return this.mapperUtil.getModelMapper().map(
                this.doctorRepository
                        .findById(id).orElseThrow(()
                                -> new RuntimeException("Doctor with id=" + id + " not found!")), DoctorDTO.class);
    }

    @Override
    public CreateDoctorDTO createDoctor(CreateDoctorDTO doctor) {
        return mapperUtil.getModelMapper()
                .map(this.doctorRepository
                        .save(mapperUtil.getModelMapper()
                                .map(doctor, Doctor.class)), CreateDoctorDTO.class);
    }

    @Override
    public UpdateDoctorDTO updateDoctor(UpdateDoctorDTO doctor, long id) {
        Doctor existingDoctor = this.doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id=" + id + " not found!"));

        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialty(doctor.getSpecialty());

        Doctor updatedDoctor = this.doctorRepository.save(existingDoctor);

        return mapperUtil.getModelMapper().map(updatedDoctor, UpdateDoctorDTO.class);
    }

    @Override
    public void deleteDoctor(long id) {
        this.doctorRepository.deleteById(id);
    }

    @Override
    public Doctor findDoctorWithMostSickLeaves() {
        List<Doctor> doctors = doctorRepository.findAll();

        Doctor topDoctor = null;
        long maxSickLeaves = 0;

        for (Doctor doctor : doctors) {
            long sickLeaveCount = doctor.getSickLeaves().size();

            if (sickLeaveCount > maxSickLeaves) {
                topDoctor = doctor;
                maxSickLeaves = sickLeaveCount;
            }
        }

        if (topDoctor != null) {
            System.out.println("Doctor with most sick leaves: "
                    + topDoctor.getName() + ", Count: " + maxSickLeaves);
            return topDoctor;
        }

        return null;
    }

    @Override
    public Map<Long, Integer> getNumberOfExamsPerDoctor() {
        List<Doctor> doctors = doctorRepository.findAll();

        Map<Long, Integer> doctorVisitCountMap = new HashMap<>();

        for (Doctor doctor : doctors) {
            int visitCount = doctor.getExams().size();
            doctorVisitCountMap.put(doctor.getId(), visitCount);
        }

        return doctorVisitCountMap;
    }

    @Override
    public List<ExamDTO> getExamsByDoctor(long doctorId) {
        return examRepository.findByDoctorId(doctorId).stream()
                .map(exam -> {
                    ExamDTO dto = new ExamDTO();
                    dto.setId(exam.getId());
                    dto.setDate(exam.getDate());
                    dto.setPatientName(exam.getPatient() != null ? exam.getPatient().getName() : "Unknown Patient");
                    dto.setDiagnosisName(exam.getDiagnosis() != null ? exam.getDiagnosis().getName() : "No Diagnosis");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getGpDoctors() {
        List<Doctor> gpDoctors = doctorRepository.findByGpTrue();

        return gpDoctors.stream()
                .map(doctor -> mapperUtil.getModelMapper().map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO getDoctorWithGpPatients(long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id=" + id + " not found!"));

        DoctorDTO doctorDTO = mapperUtil.getModelMapper().map(doctor, DoctorDTO.class);
        doctorDTO.setGpPatients(doctor.getGpPatients()
                .stream()
                .map(Patient::getName)
                .collect(Collectors.toList()));

        return doctorDTO;
    }
}
