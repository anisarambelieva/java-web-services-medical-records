package com.cscb869_medical_records.dto.doctor;

import com.cscb869_medical_records.data.entity.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateDoctorDTO {
    @NotBlank
    @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String name;

    private Specialty specialty;

    private boolean gp;
}
