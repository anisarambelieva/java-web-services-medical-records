package com.cscb869_medical_records.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Patient extends BaseEntity {
    @NotBlank
    @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String name;

    @NotBlank
    @Size(min = 10, max = 10, message="PIN's length must be 10")
    private String pin;

    private LocalDate paidInsuranceDate;

    @ManyToOne
    private Doctor doctor;

    @ManyToMany
    @JsonIgnore
    private Set<Diagnosis> diagnosis;

    @OneToMany
    @JsonIgnore
    private Set<Exam> exams;

    @OneToMany
    @JsonIgnore
    private Set<SickLeave> sickLeaves;

    @ManyToOne(optional = false)
    private Doctor gp;
}
