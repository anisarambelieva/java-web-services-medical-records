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

@Controller
@RequiredArgsConstructor
@RequestMapping("/sick-leaves")
public class SickLeaveViewController {

    private final SickLeaveService sickLeaveService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final ExamService examService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public String getSickLeaves(Model model) {
        List<SickLeaveViewModel> sickLeaves = mapperUtil
                .mapList(this.sickLeaveService.getAllSickLeaves(), SickLeaveViewModel.class);
        model.addAttribute("sickLeaves", sickLeaves);
        return "/sick-leaves/sick-leaves";
    }

    @GetMapping("/create")
    public String showCreateSickLeaveForm(
            @RequestParam(value = "examId", required = false) Long examId,
            Model model
    ) {
        CreateSickLeaveViewModel sickLeave = new CreateSickLeaveViewModel();
        if (examId != null) {
            sickLeave.setExamId(examId); // Pre-fill examId if passed
        }

        model.addAttribute("sickLeave", sickLeave);
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("exams", examService.getExaminations());
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
            model.addAttribute("exams", examService.getExaminations());
            return "/sick-leaves/create-sick-leave";
        }

        CreateSickLeaveDTO sickLeaveDTO = mapperUtil.getModelMapper().map(sickLeave, CreateSickLeaveDTO.class);
        sickLeaveService.createSickLeave(sickLeaveDTO);
        return "redirect:/sick-leaves";
    }

    @GetMapping("/edit/{id}")
    public String showEditSickLeaveForm(Model model, @PathVariable Long id) {
        SickLeaveDTO sickLeave = sickLeaveService.getSickLeaveById(id);
        UpdateSickLeaveViewModel updateSickLeave = mapperUtil.getModelMapper().map(sickLeave, UpdateSickLeaveViewModel.class);

        model.addAttribute("sickLeave", updateSickLeave);
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("exams", examService.getExaminations());

        return "/sick-leaves/edit-sick-leave";
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
            model.addAttribute("exams", examService.getExaminations());
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
