package com.cscb869_medical_records.web.view.controller.model.exam;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamViewModel {
    private long id;         // Unique ID for the exam
    private String doctorName; // Name of the doctor
    private String patientName; // Name of the patient
    private String diagnosisName; // Name of the diagnosis
    private LocalDate date;  // Examination date
}
