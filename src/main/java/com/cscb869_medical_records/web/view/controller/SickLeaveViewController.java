package com.cscb869_medical_records.web.view.controller;

import com.cscb869_medical_records.dto.sickleave.CreateSickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.SickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.UpdateSickLeaveDTO;
import com.cscb869_medical_records.service.DoctorService;
import com.cscb869_medical_records.service.PatientService;
import com.cscb869_medical_records.service.SickLeaveService;
import com.cscb869_medical_records.service.ExamService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.sickleave.CreateSickLeaveViewModel;
import com.cscb869_medical_records.web.view.controller.model.sickleave.SickLeaveViewModel;
import com.cscb869_medical_records.web.view.controller.model.sickleave.UpdateSickLeaveViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sick-leaves")
public class SickLeaveViewController {

    private final SickLeaveService sickLeaveService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public String getSickLeaves(Model model) {
        List<SickLeaveViewModel> sickLeaves = sickLeaveService.getAllSickLeaves().stream()
                .map(sickLeave -> {
                    SickLeaveViewModel viewModel = new SickLeaveViewModel();
                    viewModel.setId(sickLeave.getId());
                    viewModel.setStartDate(sickLeave.getStartDate());
                    viewModel.setCountDays(sickLeave.getCountDays());

                    if (sickLeave.getDoctorId() != null) {
                        viewModel.setDoctorName(doctorService.getDoctor(sickLeave.getDoctorId()).getName());
                    }

                    if (sickLeave.getPatientId() != null) {
                        viewModel.setPatientName(patientService.getPatient(sickLeave.getPatientId()).getName());
                    }

                    return viewModel;
                })
                .collect(Collectors.toList());

        model.addAttribute("sickLeaves", sickLeaves);
        return "/sick-leaves/sick-leaves";
    }



    @GetMapping("/create")
    public String showCreateSickLeaveForm(
            Model model
    ) {
        CreateSickLeaveViewModel sickLeave = new CreateSickLeaveViewModel();

        model.addAttribute("sickLeave", sickLeave);
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getPatients());

        return "/sick-leaves/create-sick-leave";
    }

    @PostMapping("/create")
    public String createSickLeave(
            @Valid @ModelAttribute("sickLeave") CreateSickLeaveViewModel sickLeave,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            model.addAttribute("patients", patientService.getPatients());

            return "/sick-leaves/create-sick-leave";
        }

        CreateSickLeaveDTO sickLeaveDTO = mapperUtil.getModelMapper().map(sickLeave, CreateSickLeaveDTO.class);
        sickLeaveService.createSickLeave(sickLeaveDTO);
        return "redirect:/sick-leaves";
    }

    @GetMapping("/edit/{id}")
    public String showEditSickLeaveForm(Model model, @PathVariable Long id) {
        SickLeaveDTO sickLeave = sickLeaveService.getSickLeaveById(id);
        UpdateSickLeaveViewModel updateSickLeave = new UpdateSickLeaveViewModel();

        updateSickLeave.setId(sickLeave.getId());
        updateSickLeave.setStartDate(sickLeave.getStartDate());
        updateSickLeave.setCountDays(sickLeave.getCountDays());

        if (sickLeave.getDoctorId() != null) {
            updateSickLeave.setDoctorId(sickLeave.getDoctorId());
            updateSickLeave.setDoctorName(doctorService.getDoctor(sickLeave.getDoctorId()).getName());
        } else {
            updateSickLeave.setDoctorName("Not Assigned");
        }

        if (sickLeave.getPatientId() != null) {
            updateSickLeave.setPatientId(sickLeave.getPatientId());
            updateSickLeave.setPatientName(patientService.getPatient(sickLeave.getPatientId()).getName());
        } else {
            updateSickLeave.setPatientName("Not Assigned");
        }

        model.addAttribute("sickLeave", updateSickLeave);
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getPatients());

        return "sick-leaves/edit-sick-leave";
    }


    @PostMapping("/update/{id}")
    public String updateSickLeave(
            @PathVariable long id,
            @Valid @ModelAttribute("sickLeave") UpdateSickLeaveViewModel sickLeave,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            model.addAttribute("patients", patientService.getPatients());

            return "/sick-leaves/edit-sick-leave";
        }

        UpdateSickLeaveDTO sickLeaveDTO = mapperUtil.getModelMapper().map(sickLeave, UpdateSickLeaveDTO.class);

        sickLeaveService.updateSickLeave(id, sickLeaveDTO);
        return "redirect:/sick-leaves";
    }


    @GetMapping("/delete/{id}")
    public String deleteSickLeave(@PathVariable long id) {
        sickLeaveService.deleteSickLeave(id);
        return "redirect:/sick-leaves";
    }
}
