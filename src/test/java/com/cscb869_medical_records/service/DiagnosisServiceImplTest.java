package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.repo.DiagnosisRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.dto.diagnosis.UpdateDiagnosisDTO;
import com.cscb869_medical_records.service.Impl.DiagnosisServiceImpl;
import com.cscb869_medical_records.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; // includes lenient()

@ExtendWith(MockitoExtension.class)
class DiagnosisServiceImplTest {

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DiagnosisServiceImpl diagnosisService;

    @BeforeEach
    void setUp() {
        lenient().when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
    }

    private Diagnosis createDiagnosis(String name, Long id) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(name);

        if (id != null) {
            ReflectionTestUtils.setField(diagnosis, "id", id);
        }
        return diagnosis;
    }

    @Test
    void testGetDiagnoses() {
        Diagnosis diagnosis1 = createDiagnosis("Diagnosis1", 1L);
        Diagnosis diagnosis2 = createDiagnosis("Diagnosis2", 2L);
        List<Diagnosis> diagnosisList = Arrays.asList(diagnosis1, diagnosis2);
        when(diagnosisRepository.findAll()).thenReturn(diagnosisList);

        List<Diagnosis> result = diagnosisService.getDiagnoses();

        assertEquals(2, result.size());
        assertEquals("Diagnosis1", result.get(0).getName());
        assertEquals("Diagnosis2", result.get(1).getName());
    }

    @Test
    void testGetDiagnosisFound() {
        Diagnosis diagnosis = createDiagnosis("Test Diagnosis", 1L);
        when(diagnosisRepository.findById(1L)).thenReturn(Optional.of(diagnosis));

        Diagnosis result = diagnosisService.getDiagnosis(1L);

        assertNotNull(result);
        assertEquals("Test Diagnosis", result.getName());
    }

    @Test
    void testGetDiagnosisNotFound() {
        when(diagnosisRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                diagnosisService.getDiagnosis(1L));
        assertTrue(exception.getMessage().contains("Diagnosis with id=1 not found!"));
    }

    @Test
    void testCreateDiagnosis() {
        Diagnosis diagnosisToCreate = createDiagnosis("New Diagnosis", null);
        Diagnosis savedDiagnosis = createDiagnosis("New Diagnosis", 1L);
        when(diagnosisRepository.save(diagnosisToCreate)).thenReturn(savedDiagnosis);

        Diagnosis result = diagnosisService.createDiagnosis(diagnosisToCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Diagnosis", result.getName());
    }

    @Test
    void testUpdateDiagnosis() {
        long id = 1L;
        Diagnosis existingDiagnosis = createDiagnosis("Old Name", id);
        when(diagnosisRepository.findById(id)).thenReturn(Optional.of(existingDiagnosis));

        UpdateDiagnosisDTO updateDTO = new UpdateDiagnosisDTO();
        updateDTO.setName("New Name");

        Diagnosis updatedDiagnosis = createDiagnosis("New Name", id);
        when(diagnosisRepository.save(existingDiagnosis)).thenReturn(updatedDiagnosis);

        when(modelMapper.map(any(), eq(UpdateDiagnosisDTO.class))).thenReturn(updateDTO);

        UpdateDiagnosisDTO result = diagnosisService.updateDiagnosis(updateDTO, id);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    @Test
    void testDeleteDiagnosis() {
        long id = 1L;
        Diagnosis diagnosis = createDiagnosis("Test Diagnosis", id);
        when(diagnosisRepository.findById(id)).thenReturn(Optional.of(diagnosis));

        doNothing().when(examRepository).updateDiagnosisToNull(id);
        doNothing().when(diagnosisRepository).deleteById(id);

        diagnosisService.deleteDiagnosis(id);

        verify(examRepository, times(1)).updateDiagnosisToNull(id);
        verify(diagnosisRepository, times(1)).deleteById(id);
    }
}
