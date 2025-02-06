package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.dto.exam.CreateExamDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.dto.exam.UpdateExamDTO;

import java.util.List;

public interface ExamService {
    List<ExamDTO> getExaminations();
    ExamDTO getExamination(long id);
    CreateExamDTO createExamination(CreateExamDTO exam);
    UpdateExamDTO updateExamination(UpdateExamDTO exam, long id);
    void deleteExamination(long id);
    String getMostCommonDiagnosis();
    int getExamCountByDoctor(long doctorId);
    List<ExamDTO> getExaminationsByDoctor(Long doctorId);
}
