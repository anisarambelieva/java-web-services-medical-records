package com.cscb869_medical_records.web.view.controller;

import com.cscb869_medical_records.data.entity.Specialty;
import com.cscb869_medical_records.dto.doctor.CreateDoctorDTO;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.service.DoctorService;
import com.cscb869_medical_records.service.ExamService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.doctor.CreateDoctorViewModel;
import com.cscb869_medical_records.web.view.controller.model.doctor.DoctorViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorViewController {
    private final DoctorService doctorService;
    private final ExamService examService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public String getDoctors(Model model) {
        List<DoctorViewModel> doctors = mapperUtil
                .mapList(this.doctorService.getDoctors(), DoctorViewModel.class);
        model.addAttribute("doctors", doctors);
        return "/doctors/doctors.html";
    }

    @GetMapping("/create-doctor")
    public String showCreateDoctorForm(Model model) {
        model.addAttribute("doctor", new CreateDoctorViewModel());
        model.addAttribute("specialties", Specialty.values());
        return "/doctors/create-doctor";
    }

    @PostMapping("/create")
    public String createDoctor(@Valid @ModelAttribute("doctor") CreateDoctorViewModel doctor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties", Specialty.values());
            return "/doctors/create-doctor";
        }
        this.doctorService.createDoctor(mapperUtil.getModelMapper().map(doctor, CreateDoctorDTO.class));
        return "redirect:/doctors";
    }

    @GetMapping("/edit-doctor/{id}")
    public String showEditDoctorForm(Model model, @PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctor(id);
        model.addAttribute("doctor", doctor);
        model.addAttribute("specialties", Specialty.values());
        return "/doctors/edit-doctor";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable long id, @Valid @ModelAttribute("doctor") UpdateDoctorDTO doctor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialties", Specialty.values());
            return "/doctors/edit-doctor";
        }
        this.doctorService.updateDoctor(doctor, id);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable int id) {
        this.doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/info")
    public String getDoctorInfo(@PathVariable Long id, Model model) {
        DoctorDTO doctor = doctorService.getDoctorWithGpPatients(id);
        int examCount = examService.getExamCountByDoctor(id);
        List<ExamDTO> exams = examService.getExaminationsByDoctor(id);

        model.addAttribute("doctor", doctor);
        model.addAttribute("examCount", examCount);
        model.addAttribute("exams", exams);
        model.addAttribute("gpPatients", doctor.getGpPatients());

        return "/doctors/doctor-info";
    }
}
