package com.cscb869_medical_records.web.api;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.dto.diagnosis.UpdateDiagnosisDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.service.DiagnosisService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.diagnosis.UpdateDiagnosisViewModel;
import com.cscb869_medical_records.web.view.controller.model.doctor.UpdateDoctorViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diagnosis")
public class DiagnosisApiController {
    private final DiagnosisService diagnosisService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public List<Diagnosis> getDiagnosis() { return this.diagnosisService.getDiagnoses(); }

    @GetMapping("/{id}")
    public Diagnosis getDiagnosis(@PathVariable long id) { return this.diagnosisService.getDiagnosis(id); }

    @PostMapping
    public Diagnosis createDiagnosis(@RequestBody Diagnosis diagnosis) {
        return this.diagnosisService.createDiagnosis(diagnosis);
    }

    @PutMapping("/{id}")
    public UpdateDiagnosisViewModel updateDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        return mapperUtil.getModelMapper().map(this.diagnosisService.
                updateDiagnosis(mapperUtil.getModelMapper().map(diagnosis, UpdateDiagnosisDTO.class), id), UpdateDiagnosisViewModel.class);
    }

    @DeleteMapping("/{id}")
    public void DeleteDiagnosis(@PathVariable long id) { this.diagnosisService.deleteDiagnosis(id); }
}

