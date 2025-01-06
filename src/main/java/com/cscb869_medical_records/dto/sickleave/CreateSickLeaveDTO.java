package com.cscb869_medical_records.dto.sickleave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateSickLeaveDTO {
    private LocalDate startDate;
    private int countDays;
    private Long examId;
    private Long doctorId;
    private Long patientId;
}