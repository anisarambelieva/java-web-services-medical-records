

package com.cscb869_medical_records.web.view.controller;

import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import com.cscb869_medical_records.dto.patient.PatientDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.service.DoctorService;
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
    private final DoctorService doctorService;
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

        List<DoctorDTO> gpDoctors = doctorService.getGpDoctors();
        model.addAttribute("gpDoctors", gpDoctors);

        return "/patients/create-patient";
    }

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/patients/create-patient";
        }
        this.patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String showEditPatientForm(Model model, @PathVariable Long id) {
        UpdatePatientDTO patient = mapperUtil.getModelMapper()
                .map(patientService.getPatient(id), UpdatePatientDTO.class);

        model.addAttribute("patient", patient);

        List<DoctorDTO> gpDoctors = doctorService.getGpDoctors();
        model.addAttribute("gpDoctors", gpDoctors);

        return "/patients/edit-patient";
    }



    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable long id, @Valid @ModelAttribute("patient") UpdatePatientDTO patient,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("gpDoctors", doctorService.getGpDoctors());
            return "/patients/edit-patient";
        }

        this.patientService.updatePatient(patient, id);
        return "redirect:/patients";
    }


    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        this.patientService.deletePatient(id);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/info")
    public String viewPatientInfo(@PathVariable Long id, Model model) {
        PatientDTO patient = patientService.getPatient(id);
        List<String> diagnosisHistory = patientService.getPatientDiagnosisHistory(id);
        List<String> doctorsVisited = patientService.getDoctorsVisited(id);
        List<String> sickLeaves = patientService.getSickLeaves(id);

        DoctorDTO gpDoctor = patient.getGp() != null ? mapperUtil.getModelMapper().map(patient.getGp(), DoctorDTO.class) : null;

        model.addAttribute("patient", patient);
        model.addAttribute("sickLeaves", sickLeaves);
        model.addAttribute("doctorsVisited", doctorsVisited);
        model.addAttribute("diagnosisHistory", diagnosisHistory);
        model.addAttribute("gpDoctor", gpDoctor);

        return "/patients/patient-info";
    }
}

