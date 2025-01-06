package com.cscb869_medical_records.web.view.controller;

import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.service.PatientService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.patient.PatientViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientViewController {

    private final PatientService patientService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public String getPatients(Model model) {
        List<PatientViewModel> patients = mapperUtil
                .mapList(this.patientService.getPatients(), PatientViewModel.class);
        model.addAttribute("patients", patients);
        return "/patients/patients.html";
    }

    @GetMapping("/create-patient")
    public String showCreatePatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "/patients/create-patient";
    }

    @PostMapping("/create")
    public String createPatient(Patient patient) {
        this.patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String showEditPatientForm(Model model, @PathVariable Long id) {
        model.addAttribute("patient", this.patientService.getPatient(id));
        return "/patients/edit-patient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable long id, @Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/patients/edit-patient";
        }
        this.patientService.updatePatient(mapperUtil.getModelMapper().map(patient, UpdatePatientDTO.class), id);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        this.patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
