package com.cscb869_medical_records.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Diagnosis extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "diagnosis")
    @JsonIgnore
    private Set<Patient> patients;

    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Exam> exams;
}

