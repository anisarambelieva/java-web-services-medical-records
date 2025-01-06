package com.cscb869_medical_records.web.view.controller.model.sickleave;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSickLeaveViewModel {
    @NotNull(message = "Start date must not be null")
    private LocalDate startDate;

    @Min(value = 1, message = "Number of days must be at least 1")
    private int countDays;

    @Min(value = 1, message = "Exam must be selected")
    private Long examId;

    @Min(value = 1, message = "Doctor must be selected")
    private Long doctorId;

    @Min(value = 1, message = "Patient must be selected")
    private Long patientId;
}
