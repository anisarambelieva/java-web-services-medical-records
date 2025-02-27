package com.cscb869_medical_records.dto.patient;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientDTO {
    private long id;

    @NotBlank
    @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String name;

    @NotBlank
    @Size(min = 10, max = 10, message="PIN's length must be 10")
    private String pin;
    private LocalDate paidInsuranceDate;

    private List<String> diagnoses;
    private List<String> doctorsVisited;
    private List<String> sickLeaves;
    private DoctorDTO gp;
}
