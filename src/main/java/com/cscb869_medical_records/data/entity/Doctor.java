package com.cscb869_medical_records.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Doctor extends BaseEntity {

    @NotBlank
    @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String name;

    private Specialty specialty;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Patient> patients;

    @OneToMany
    @JsonIgnore
    private Set<Exam> exams;

    @OneToMany
    @JsonIgnore
    private Set<SickLeave> sickLeaves;
}
