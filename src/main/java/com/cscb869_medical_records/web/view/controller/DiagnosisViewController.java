package com.cscb869_medical_records.web.view.controller;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.dto.diagnosis.UpdateDiagnosisDTO;
import com.cscb869_medical_records.service.DiagnosisService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.diagnosis.DiagnosisViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diagnoses")
public class DiagnosisViewController {

    private final DiagnosisService diagnosisService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public String getDiagnoses(Model model) {
        List<DiagnosisViewModel> diagnoses = mapperUtil
                .mapList(this.diagnosisService.getDiagnoses(), DiagnosisViewModel.class);
        model.addAttribute("diagnoses", diagnoses);
        return "/diagnoses/diagnoses.html";
    }

    @GetMapping("/create-diagnosis")
    public String showCreateDiagnosisForm(Model model) {
        model.addAttribute("diagnosis", new Diagnosis());
        return "/diagnoses/create-diagnosis";
    }

    @PostMapping("/create")
    public String createDiagnosis(@Valid @ModelAttribute("diagnosis") Diagnosis diagnosis, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/diagnoses/create-diagnosis";
        }
        this.diagnosisService.createDiagnosis(diagnosis);
        return "redirect:/diagnoses";
    }

    @GetMapping("/edit-diagnosis/{id}")
    public String showEditDiagnosisForm(Model model, @PathVariable Long id) {
        model.addAttribute("diagnosis", this.diagnosisService.getDiagnosis(id));
        return "/diagnoses/edit-diagnosis";
    }

    @PostMapping("/update/{id}")
    public String updateDiagnosis(@PathVariable long id, @Valid @ModelAttribute("diagnosis") Diagnosis diagnosis, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/diagnoses/edit-diagnosis";
        }
        this.diagnosisService.updateDiagnosis(mapperUtil.getModelMapper().map(diagnosis, UpdateDiagnosisDTO.class), id);
        return "redirect:/diagnoses";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable long id) {
        this.diagnosisService.deleteDiagnosis(id);
        return "redirect:/diagnoses";
    }
}
