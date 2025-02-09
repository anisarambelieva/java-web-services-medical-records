package com.cscb869_medical_records.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePatientDTO {
    private Long id;

    @NotBlank
    @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String name;

    @NotBlank
    @Size(min = 10, max = 10, message="PIN's length must be 10")
    private String pin;

    private LocalDate paidInsuranceDate;
    private Long gpId;
}
