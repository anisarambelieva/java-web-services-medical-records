package com.cscb869_medical_records.dto.sickleave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SickLeaveDTO {
    private Long id;
    private LocalDate startDate;
    private int countDays;
    private String examDetails;
    private String doctorName;
    private String patientName;
}
