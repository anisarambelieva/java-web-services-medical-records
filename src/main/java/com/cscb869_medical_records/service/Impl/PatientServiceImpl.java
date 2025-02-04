package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.data.repo.SickLeaveRepository;
import com.cscb869_medical_records.dto.patient.PatientDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.service.PatientService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ExamRepository examRepository;
    private final SickLeaveRepositowry sickLeaveRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<PatientDTO> getPatients() {
        return this.mapperUtil.mapList(this.patientRepository.findAll(), PatientDTO.class);
    }

    @Override
    public PatientDTO getPatient(long id) {
        Patient patient = this.patientRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Patient with id=" + id + " not found!"));

        List<String> diagnoses = patient.getExams().stream()
                .filter(exam -> exam.getDiagnosis() != null)
                .map(exam -> exam.getDiagnosis().getName())
                .distinct()
                .toList();

        List<String> doctorsVisited = patient.getExams().stream()
                .map(exam -> exam.getDoctor().getName())
                .distinct()
                .toList();

        List<String> sickLeaves = patient.getSickLeaves().stream()
                .map(sickLeave -> "From " + sickLeave.getStartDate() + " for " + sickLeave.getCountDays() + " days")
                .toList();

        PatientDTO patientDTO = mapperUtil.getModelMapper().map(patient, PatientDTO.class);
        patientDTO.setDiagnoses(diagnoses);
        patientDTO.setDoctorsVisited(doctorsVisited);
        patientDTO.setSickLeaves(sickLeaves);

        return patientDTO;
    }



    @Override
    public Patient createPatient(Patient patient) {
        return this.patientRepository.save(patient);
    }

    @Override
    public UpdatePatientDTO updatePatient(UpdatePatientDTO patient, long id) {
        return mapperUtil.getModelMapper()
                .map(this.patientRepository.findById(id)
                        .map(patient1 -> {
                            patient1.setName(patient.getName());
                            patient1.setPin(patient.getPin());
                            patient1.setPaidInsuranceDate(patient.getPaidInsuranceDate());
                            return this.patientRepository.save(patient1);
                        }), UpdatePatientDTO.class);
    }

    @Override
    public void deletePatient(long id) {
        this.patientRepository.deleteById(id);
    }

    @Override
    public List<String> getPatientDiagnosisHistory(Long patientId) {
        List<Exam> exams = examRepository.findByPatientId(patientId);

        return exams.stream()
                .map(exam -> exam.getDiagnosis() != null ? exam.getDiagnosis().getName() : "No Diagnosis")
                .distinct() // Avoid duplicate diagnoses
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDoctorsVisited(Long patientId) {
        return examRepository.findByPatientId(patientId)
                .stream()
                .map(exam -> exam.getDoctor().getName())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSickLeaves(Long patientId) {
        return sickLeaveRepository.findByPatientId(patientId)
                .stream()
                .map(sickLeave ->
                        "From: " + sickLeave.getStartDate() +
                                " | Days: " + sickLeave.getCountDays()
                ) // Format output
                .collect(Collectors.toList());
    }
}
