package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.dto.patient.PatientDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.service.PatientService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<PatientDTO> getPatients() {
        return this.mapperUtil.mapList(this.patientRepository.findAll(), PatientDTO.class);
    }

    @Override
    public PatientDTO getPatient(long id) {
        return this.mapperUtil.getModelMapper().map(
                this.patientRepository
                        .findById(id).orElseThrow(()
                                -> new RuntimeException("Patient with id=" + id + " not found!" )), PatientDTO.class);
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
    public List<Patient> getAllByDiagnosisNameContains(String diagnosisName) {
        return this.patientRepository.findAllByDiagnosisNameContains(diagnosisName);
    }
}
