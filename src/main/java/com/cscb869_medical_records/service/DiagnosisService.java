package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.dto.diagnosis.UpdateDiagnosisDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;

import java.util.List;

public interface DiagnosisService {
    List<Diagnosis> getDiagnoses();
    Diagnosis getDiagnosis(long id);
    Diagnosis createDiagnosis(Diagnosis diagnosis);
    UpdateDiagnosisDTO updateDiagnosis(UpdateDiagnosisDTO diagnosis, long id);
    void deleteDiagnosis(long id);
    List<Patient> getPatientsWithDiagnosis(long diagnosisId);
}
