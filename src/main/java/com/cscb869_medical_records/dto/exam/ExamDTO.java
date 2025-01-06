package com.cscb869_medical_records.dto.exam;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamDTO {
    private long id;
    private LocalDate date;
    private Long patientId;
    private Long doctorId;
    private Long diagnosisId;
    private String diagnosisName;
    private String patientName;
    private String doctorName;
}
