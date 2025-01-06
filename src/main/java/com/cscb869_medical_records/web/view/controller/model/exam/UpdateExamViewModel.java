package com.cscb869_medical_records.web.view.controller.model.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExamViewModel {
    private long id;
    private long doctorId;
    private long patientId;
    private long diagnosisId;
    private LocalDate date;
}
