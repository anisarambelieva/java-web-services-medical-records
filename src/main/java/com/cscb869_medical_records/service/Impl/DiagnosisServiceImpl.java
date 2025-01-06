package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.repo.DiagnosisRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.dto.diagnosis.UpdateDiagnosisDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.service.DiagnosisService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("Diagnosis with id=" + id + " not found!" ));
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
}
