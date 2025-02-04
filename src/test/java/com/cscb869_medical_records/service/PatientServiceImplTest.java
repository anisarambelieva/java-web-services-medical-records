package com.cscb869_medical_records.service.Impl;

import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.data.repo.SickLeaveRepository;
import com.cscb869_medical_records.dto.patient.PatientDTO;
import com.cscb869_medical_records.dto.patient.UpdatePatientDTO;
import com.cscb869_medical_records.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private SickLeaveRepository sickLeaveRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    void setUp() {
        lenient().when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
    }

    @Test
    void testGetPatients() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(new Patient(), new Patient()));
        when(mapperUtil.mapList(anyList(), eq(PatientDTO.class))).thenReturn(Arrays.asList(new PatientDTO(), new PatientDTO()));

        List<PatientDTO> result = patientService.getPatients();

        assertEquals(2, result.size());
    }

    @Test
    void testGetPatientFound() {
        Patient patient = new Patient();
        patient.setExams(Collections.emptySet()); // Ensure exams set is initialized
        patient.setSickLeaves(Collections.emptySet()); // Ensure sick leaves set is initialized

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(new PatientDTO());

        PatientDTO result = patientService.getPatient(1L);

        assertNotNull(result);
    }

    @Test
    void testGetPatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> patientService.getPatient(1L));
        assertTrue(exception.getMessage().contains("Patient with id=1 not found!"));
    }

//    @Test
//    void testUpdatePatient() {
//        long id = 1L;
//        Patient existingPatient = new Patient();
//        when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));
//
//        UpdatePatientDTO updateDTO = new UpdatePatientDTO();
//        updateDTO.setName("Updated Name");
//        updateDTO.setPin("1234567890");
//
//        Patient updatedPatient = new Patient();
//        when(patientRepository.save(existingPatient)).thenReturn(updatedPatient);
//        doReturn(updateDTO).when(modelMapper).map(updatedPatient, UpdatePatientDTO.class);
//
//        UpdatePatientDTO result = patientService.updatePatient(updateDTO, id);
//
//        assertNotNull(result);
//        assertEquals("Updated Name", result.getName());
//    }

    @Test
    void testDeletePatient() {
        long id = 1L;
        doNothing().when(patientRepository).deleteById(id);

        patientService.deletePatient(id);

        verify(patientRepository, times(1)).deleteById(id);
    }
}
