package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.dto.patient.PatientDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;

import java.util.List;

public interface PatientService {
    List<PatientDTO> getPatients();
    PatientDTO getPatient(long id);
    Patient createPatient(Patient Patient);
    UpdatePatientDTO updatePatient(UpdatePatientDTO Patient, long id);
    void deletePatient(long id);
    List<String> getPatientDiagnosisHistory(Long id);
    List<String> getDoctorsVisited(Long id);
    List<String> getSickLeaves(Long patientId);
}
