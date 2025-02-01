package com.cscb869_medical_records.web.view.controller.model.exam;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamViewModel {
    private long id;
    private String doctorName;
    private String patientName;
    private String diagnosisName;
    private LocalDate date;
}
