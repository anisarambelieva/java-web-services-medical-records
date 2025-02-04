package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.entity.SickLeave;
import com.cscb869_medical_records.data.repo.DoctorRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.data.repo.SickLeaveRepository;
import com.cscb869_medical_records.dto.sickleave.CreateSickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.SickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.UpdateSickLeaveDTO;
import com.cscb869_medical_records.service.SickLeaveService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MapperUtil mapperUtil;

    private static final Logger log = LoggerFactory.getLogger(SickLeaveServiceImpl.class);


    @Override
    public List<SickLeaveDTO> getAllSickLeaves() {
        return mapperUtil.mapList(sickLeaveRepository.findAll(), SickLeaveDTO.class);
    }

    @Override
    public SickLeaveDTO getSickLeaveById(long id) {
        SickLeave sickLeave = sickLeaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SickLeave with id=" + id + " not found!"));
        return mapperUtil.getModelMapper().map(sickLeave, SickLeaveDTO.class);
    }

    @Override
    public CreateSickLeaveDTO createSickLeave(CreateSickLeaveDTO sickLeaveDTO) {
        SickLeave sickLeave = mapperUtil.getModelMapper().map(sickLeaveDTO, SickLeave.class);

        sickLeave.setDoctor(doctorRepository.findById(sickLeaveDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor with ID=" + sickLeaveDTO.getDoctorId() + " not found!")));

        sickLeave.setPatient(patientRepository.findById(sickLeaveDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient with ID=" + sickLeaveDTO.getPatientId() + " not found!")));

        return mapperUtil.getModelMapper().map(sickLeaveRepository.save(sickLeave), CreateSickLeaveDTO.class);
    }

    @Override
    public UpdateSickLeaveDTO updateSickLeave(long id, UpdateSickLeaveDTO leaveDTO) {
        SickLeave existingSickLeave = sickLeaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SickLeave with id=" + id + " not found!"));

        existingSickLeave.setStartDate(leaveDTO.getStartDate());
        existingSickLeave.setCountDays(leaveDTO.getCountDays());

        if (leaveDTO.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(leaveDTO.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor with id=" + leaveDTO.getDoctorId() + " not found!"));
            existingSickLeave.setDoctor(doctor);
        }

        if (leaveDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(leaveDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient with id=" + leaveDTO.getPatientId() + " not found!"));
            existingSickLeave.setPatient(patient);
        }

        SickLeave updatedSickLeave = sickLeaveRepository.save(existingSickLeave);

        return mapperUtil.getModelMapper().map(updatedSickLeave, UpdateSickLeaveDTO.class);
    }


    @Override
    public void deleteSickLeave(long id) {
        if (!sickLeaveRepository.existsById(id)) {
            throw new RuntimeException("SickLeave with id=" + id + " does not exist!");
        }
        sickLeaveRepository.deleteById(id);
    }
}
