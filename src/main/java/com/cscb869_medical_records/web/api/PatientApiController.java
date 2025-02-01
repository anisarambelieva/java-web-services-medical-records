package com.cscb869_medical_records.web.api;

import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.dto.patient.PatientDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.service.PatientService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.patient.UpdatePatientViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientApiController {
    private final PatientService patientService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public List<PatientDTO> getPatients() { return this.patientService.getPatients(); }

    @GetMapping("/{id}")
    public PatientDTO getPatient(@PathVariable long id) { return this.patientService.getPatient(id); }

    @PostMapping
    public Patient createPatient(@RequestBody Patient Patient) {
        return this.patientService.createPatient(Patient);
    }

    @PutMapping("/{id}")
    public UpdatePatientViewModel updatePatient(@RequestBody Patient patient, @PathVariable long id) {
        return mapperUtil.getModelMapper().map(this.patientService.
                updatePatient(mapperUtil.getModelMapper().map(patient, UpdatePatientDTO.class), id), UpdatePatientViewModel.class);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable long id) { this.patientService.deletePatient(id); }
}
