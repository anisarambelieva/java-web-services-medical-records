package com.cscb869_medical_records.web.view.controller.model.patient;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientInfoViewModel {
    private String name;
    private String pin;
    private LocalDate paidInsuranceDate;
    private List<String> diagnoses;
    private List<String> doctorsVisited;
    private List<String> treatments;
    private List<String> sickLeaves;
}
