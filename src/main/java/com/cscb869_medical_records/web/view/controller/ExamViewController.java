package com.cscb869_medical_records.web.view.controller;

import com.cscb869_medical_records.dto.exam.CreateExamDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.dto.exam.UpdateExamDTO;
import com.cscb869_medical_records.service.DiagnosisService;
import com.cscb869_medical_records.service.DoctorService;
import com.cscb869_medical_records.service.ExamService;
import com.cscb869_medical_records.service.PatientService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.exam.CreateExamViewModel;
import com.cscb869_medical_records.web.view.controller.model.exam.ExamViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamViewController {

    private final ExamService examService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public String getExams(Model model) {
        List<ExamViewModel> exams = mapperUtil
                .mapList(this.examService.getExaminations(), ExamViewModel.class);
        model.addAttribute("exams", exams);
        return "/exams/exams.html";
    }

    @GetMapping("/create-exam")
    public String showCreateExamForm(Model model) {
        model.addAttribute("exam", new CreateExamViewModel());
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("diagnoses", diagnosisService.getDiagnoses()); // Add diagnoses to the model
        return "/exams/create-exam";
    }

    @PostMapping("/create")
    public String createExamination(@Valid @ModelAttribute("exam") CreateExamViewModel exam,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("diagnoses", diagnosisService.getDiagnoses());
            return "/exams/create-exam";
        }

        CreateExamDTO examDTO = mapperUtil.getModelMapper().map(exam, CreateExamDTO.class);
        examService.createExamination(examDTO);
        return "redirect:/exams";
    }

    @GetMapping("/edit-exam/{id}")
    public String showEditExamForm(Model model, @PathVariable Long id) {
        ExamDTO exam = examService.getExamination(id);
        UpdateExamDTO updateExamDTO = new UpdateExamDTO();
        updateExamDTO.setId(exam.getId());
        updateExamDTO.setDate(exam.getDate());
        updateExamDTO.setPatientId(exam.getPatientId());
        updateExamDTO.setDoctorId(exam.getDoctorId());
        updateExamDTO.setDiagnosisId(exam.getDiagnosisId()); // Include the diagnosis ID

        model.addAttribute("exam", updateExamDTO);
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("diagnoses", diagnosisService.getDiagnoses()); // Add diagnoses to the model

        return "/exams/edit-exam";
    }

    @PostMapping("/update/{id}")
    public String updateExam(
            @PathVariable long id,
            @Valid @ModelAttribute("exam") UpdateExamDTO exam,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("diagnoses", diagnosisService.getDiagnoses());
            return "/exams/edit-exam";
        }
        examService.updateExamination(exam, id);
        return "redirect:/exams";
    }

    @GetMapping("/delete/{id}")
    public String deleteExamination(@PathVariable int id) {
        this.examService.deleteExamination(id);
        return "redirect:/exams";
    }
}
