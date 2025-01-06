package com.cscb869_medical_records.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SickLeave extends BaseEntity {
    private LocalDate startDate;
    private int countDays;

    @OneToOne
    private Exam exam;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;
}
