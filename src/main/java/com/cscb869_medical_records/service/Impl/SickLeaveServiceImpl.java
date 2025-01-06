package com.cscb869_medical_records.service.impl;

import com.cscb869_medical_records.data.entity.SickLeave;
import com.cscb869_medical_records.data.repo.SickLeaveRepository;
import com.cscb869_medical_records.dto.sickleave.CreateSickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.SickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.UpdateSickLeaveDTO;
import com.cscb869_medical_records.service.SickLeaveService;
import com.cscb869_medical_records.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;
    private final MapperUtil mapperUtil;

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
    public CreateSickLeaveDTO createSickLeave(CreateSickLeaveDTO leaveDTO) {
        SickLeave sickLeave = mapperUtil.getModelMapper().map(leaveDTO, SickLeave.class);
        SickLeave savedSickLeave = sickLeaveRepository.save(sickLeave);
        return mapperUtil.getModelMapper().map(savedSickLeave, CreateSickLeaveDTO.class);
    }

    @Override
    public UpdateSickLeaveDTO updateSickLeave(long id, UpdateSickLeaveDTO leaveDTO) {
        SickLeave existingSickLeave = sickLeaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SickLeave with id=" + id + " not found!"));

        existingSickLeave.setStartDate(leaveDTO.getStartDate());
        existingSickLeave.setCountDays(leaveDTO.getCountDays());
        existingSickLeave.setExam(null); // Set appropriate logic for handling exams
        existingSickLeave.setDoctor(null); // Set appropriate logic for handling doctors
        existingSickLeave.setPatient(null); // Set appropriate logic for handling patients

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
