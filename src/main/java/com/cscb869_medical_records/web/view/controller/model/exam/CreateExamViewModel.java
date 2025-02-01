package com.cscb869_medical_records.web.view.controller.model.exam;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateExamViewModel {
    @Min(value = 1, message = "Doctor must be selected")
    private Long doctorId;

    @Min(value = 1, message = "Patient must be selected")
    private Long patientId;

    private Long diagnosisId;

    private LocalDate date;
}
