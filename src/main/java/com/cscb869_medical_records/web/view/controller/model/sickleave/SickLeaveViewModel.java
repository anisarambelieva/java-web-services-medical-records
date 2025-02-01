package com.cscb869_medical_records.web.view.controller.model.sickleave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SickLeaveViewModel {
    private Long id;
    private LocalDate startDate;
    private int countDays;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
}
