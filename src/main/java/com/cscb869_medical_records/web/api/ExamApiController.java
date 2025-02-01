package com.cscb869_medical_records.web.api;

import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.dto.exam.CreateExamDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.dto.exam.UpdateExamDTO;
import com.cscb869_medical_records.service.ExamService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.doctor.UpdateDoctorViewModel;
import com.cscb869_medical_records.web.view.controller.model.exam.CreateExamViewModel;
import com.cscb869_medical_records.web.view.controller.model.exam.UpdateExamViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exams")
public class ExamApiController {
    private final ExamService examService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public List<ExamDTO> getExaminations() { return this.examService.getExaminations(); }

    @GetMapping("/{id}")
    public ExamDTO getExamination(@PathVariable long id) { return this.examService.getExamination(id); }

    @PostMapping
    public CreateExamViewModel createExamination(@RequestBody CreateExamViewModel exam) {
        return mapperUtil.getModelMapper().map(this.examService
                .createExamination(mapperUtil.getModelMapper().map(exam, CreateExamDTO.class)), CreateExamViewModel.class);
    }

    @PutMapping("/{id}")
    public UpdateExamViewModel updateExamination(@RequestBody Exam exam, @PathVariable long id) {
        return mapperUtil.getModelMapper().map(this.examService.
                updateExamination(mapperUtil.getModelMapper().map(exam, UpdateExamDTO.class), id), UpdateExamViewModel.class);
    }

    @DeleteMapping("/{id}")
    public void DeleteExamination(@PathVariable long id) { this.examService.deleteExamination(id); }
}