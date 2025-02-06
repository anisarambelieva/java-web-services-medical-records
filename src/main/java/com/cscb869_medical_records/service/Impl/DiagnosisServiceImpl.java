package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.DiagnosisRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.dto.diagnosis.UpdateDiagnosisDTO;
import com.cscb869_medical_records.service.DiagnosisService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final ExamRepository examRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<Diagnosis> getDiagnoses() {
        return this.diagnosisRepository.findAll();
    }

    @Override
    public Diagnosis getDiagnosis(long id) {
        return this.diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis with id=" + id + " not found!"));
    }

    @Override
    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return this.diagnosisRepository.save(diagnosis);
    }

    @Override
    public UpdateDiagnosisDTO updateDiagnosis(UpdateDiagnosisDTO diagnosis, long id) {
        return mapperUtil.getModelMapper()
                .map(this.diagnosisRepository.findById(id)
                        .map(diagnosis1 -> {
                            diagnosis1.setName(diagnosis.getName());
                            return this.diagnosisRepository.save(diagnosis1);
                        }), UpdateDiagnosisDTO.class);
    }

    @Override
    public void deleteDiagnosis(long id) {
        Diagnosis diagnosis = this.diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis with id=" + id + " not found!"));

        examRepository.updateDiagnosisToNull(diagnosis.getId());
        this.diagnosisRepository.deleteById(id);
    }

    @Override
    public List<Patient> getPatientsWithDiagnosis(long diagnosisId) {
        Diagnosis diagnosis = this.diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new RuntimeException("Diagnosis with id=" + diagnosisId + " not found!"));

        return diagnosis.getPatients().stream().collect(Collectors.toList());
    }
}
