package com.cscb869_medical_records.dto.exam;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateExamDTO {
    private Long id;
    private LocalDate date;
    private Long patientId;
    private Long doctorId;
    private Long diagnosisId;
}
