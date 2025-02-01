package com.cscb869_medical_records.dto.sickleave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSickLeaveDTO {
    private LocalDate startDate;
    private int countDays;
    private Long doctorId;
    private Long patientId;
}
