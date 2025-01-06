package com.cscb869_medical_records.service;

import com.cscb869_medical_records.dto.sickleave.CreateSickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.SickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.UpdateSickLeaveDTO;

import java.util.List;

public interface SickLeaveService {
    List<SickLeaveDTO> getAllSickLeaves();
    SickLeaveDTO getSickLeaveById(long id);
    CreateSickLeaveDTO createSickLeave(CreateSickLeaveDTO leave);
    UpdateSickLeaveDTO updateSickLeave(long id, UpdateSickLeaveDTO leave);
    void deleteSickLeave(long id);
}
