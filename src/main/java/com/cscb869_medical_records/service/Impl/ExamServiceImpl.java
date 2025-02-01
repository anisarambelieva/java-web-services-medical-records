package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.DiagnosisRepository;
import com.cscb869_medical_records.data.repo.DoctorRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.dto.exam.CreateExamDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.dto.exam.UpdateExamDTO;
import com.cscb869_medical_records.service.ExamService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<ExamDTO> getExaminations() {
        return examRepository.findAll().stream()
                .map(exam -> {
                    ExamDTO dto = new ExamDTO();
                    dto.setId(exam.getId());
                    dto.setDate(exam.getDate());
                    dto.setDoctorName(exam.getDoctor() != null ? exam.getDoctor().getName() : "Unknown Doctor");
                    dto.setPatientName(exam.getPatient() != null ? exam.getPatient().getName() : "Unknown Patient");
                    dto.setDiagnosisName(exam.getDiagnosis() != null ? exam.getDiagnosis().getName() : "No Diagnosis");
                    return dto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public ExamDTO getExamination(long id) {
        return this.mapperUtil.getModelMapper().map(
                this.examRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Exam with id=" + id + " not found!")),
                ExamDTO.class
        );
    }

    @Override
    public CreateExamDTO createExamination(CreateExamDTO examDTO) {
        Patient patient = patientRepository.findById(examDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient with id=" + examDTO.getPatientId() + " not found!"));

        Doctor doctor = doctorRepository.findById(examDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor with id=" + examDTO.getDoctorId() + " not found!"));

        Diagnosis diagnosis = null;
        if (examDTO.getDiagnosisId() != null) {
            diagnosis = diagnosisRepository.findById(examDTO.getDiagnosisId())
                    .orElseThrow(() -> new RuntimeException("Diagnosis with id=" + examDTO.getDiagnosisId() + " not found!"));
        }

        Exam exam = new Exam();
        exam.setDate(examDTO.getDate());
        exam.setPatient(patient);
        exam.setDoctor(doctor);
        exam.setDiagnosis(diagnosis);

        Exam savedExam = examRepository.save(exam);
        return mapperUtil.getModelMapper().map(savedExam, CreateExamDTO.class);
    }


    @Override
    public UpdateExamDTO updateExamination(UpdateExamDTO examDTO, long id) {
        Optional<Exam> optionalExam = examRepository.findById(id);
        if (optionalExam.isEmpty()) {
            throw new IllegalArgumentException("Exam not found with ID: " + id);
        }
        Exam existingExam = optionalExam.get();

        existingExam.setDate(examDTO.getDate());

        Doctor doctor = doctorRepository.findById(examDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + examDTO.getDoctorId()));
        existingExam.setDoctor(doctor);

        Patient patient = patientRepository.findById(examDTO.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + examDTO.getPatientId()));
        existingExam.setPatient(patient);

        Diagnosis diagnosis = diagnosisRepository.findById(examDTO.getDiagnosisId())
                .orElseThrow(() -> new IllegalArgumentException("Diagnosis not found with ID: " + examDTO.getDiagnosisId()));
        existingExam.setDiagnosis(diagnosis);

        Exam savedExam = this.examRepository.save(existingExam);
        return mapToUpdateExamDTO(savedExam);
    }

    @Override
    public void deleteExamination(long id) {
        this.examRepository.deleteById(id);
    }

    private UpdateExamDTO mapToUpdateExamDTO(Exam exam) {
        return new UpdateExamDTO(
                exam.getId(),
                exam.getDate(),
                exam.getPatient().getId(),
                exam.getDoctor().getId(),
                exam.getDiagnosis() != null ? exam.getDiagnosis().getId() : null
        );
    }
}
