package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.entity.SickLeave;
import com.cscb869_medical_records.data.repo.DoctorRepository;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.data.repo.SickLeaveRepository;
import com.cscb869_medical_records.dto.sickleave.CreateSickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.SickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.UpdateSickLeaveDTO;
import com.cscb869_medical_records.service.Impl.SickLeaveServiceImpl;
import com.cscb869_medical_records.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SickLeaveServiceImplTest {

    @Mock
    private SickLeaveRepository sickLeaveRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SickLeaveServiceImpl sickLeaveService;

    @BeforeEach
    void setUp() {
        lenient().when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
    }

    @Test
    void testGetAllSickLeaves() {
        when(sickLeaveRepository.findAll()).thenReturn(Arrays.asList(new SickLeave(), new SickLeave()));
        when(mapperUtil.mapList(anyList(), eq(SickLeaveDTO.class))).thenReturn(Arrays.asList(new SickLeaveDTO(), new SickLeaveDTO()));

        List<SickLeaveDTO> result = sickLeaveService.getAllSickLeaves();

        assertEquals(2, result.size());
    }

    @Test
    void testGetSickLeaveByIdFound() {
        SickLeave sickLeave = new SickLeave();
        when(sickLeaveRepository.findById(1L)).thenReturn(Optional.of(sickLeave));
        when(modelMapper.map(sickLeave, SickLeaveDTO.class)).thenReturn(new SickLeaveDTO());

        SickLeaveDTO result = sickLeaveService.getSickLeaveById(1L);

        assertNotNull(result);
    }

    @Test
    void testCreateSickLeave() {
        CreateSickLeaveDTO createDTO = new CreateSickLeaveDTO();
        createDTO.setDoctorId(1L);
        createDTO.setPatientId(1L);

        SickLeave sickLeave = new SickLeave();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();

        when(modelMapper.map(createDTO, SickLeave.class)).thenReturn(sickLeave);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(sickLeaveRepository.save(sickLeave)).thenReturn(sickLeave);
        when(modelMapper.map(sickLeave, CreateSickLeaveDTO.class)).thenReturn(createDTO);

        CreateSickLeaveDTO result = sickLeaveService.createSickLeave(createDTO);

        assertNotNull(result);
    }

    @Test
    void testUpdateSickLeave() {
        long id = 1L;
        SickLeave existingSickLeave = new SickLeave();
        when(sickLeaveRepository.findById(id)).thenReturn(Optional.of(existingSickLeave));

        UpdateSickLeaveDTO updateDTO = new UpdateSickLeaveDTO();
        updateDTO.setCountDays(5);

        SickLeave updatedSickLeave = new SickLeave();
        when(sickLeaveRepository.save(existingSickLeave)).thenReturn(updatedSickLeave);
        when(modelMapper.map(updatedSickLeave, UpdateSickLeaveDTO.class)).thenReturn(updateDTO);

        UpdateSickLeaveDTO result = sickLeaveService.updateSickLeave(id, updateDTO);

        assertNotNull(result);
        assertEquals(5, result.getCountDays());
    }

    @Test
    void testDeleteSickLeave() {
        long id = 1L;
        when(sickLeaveRepository.existsById(id)).thenReturn(true);
        doNothing().when(sickLeaveRepository).deleteById(id);

        sickLeaveService.deleteSickLeave(id);

        verify(sickLeaveRepository, times(1)).deleteById(id);
    }
}
