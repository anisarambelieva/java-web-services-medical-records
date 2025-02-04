package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Diagnosis;
import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.entity.Exam;
import com.cscb869_medical_records.data.entity.Patient;
import com.cscb869_medical_records.data.repo.DiagnosisRepository;
import com.cscb869_medical_records.data.repo.DoctorRepository;
import com.cscb869_medical_records.data.repo.ExamRepository;
import com.cscb869_medical_records.data.repo.PatientRepository;
import com.cscb869_medical_records.dto.exam.CreateExamDTO;
import com.cscb869_medical_records.dto.exam.ExamDTO;
import com.cscb869_medical_records.dto.exam.UpdateExamDTO;
import com.cscb869_medical_records.service.Impl.ExamServiceImpl;
import com.cscb869_medical_records.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ExamServiceImpl examService;

    @BeforeEach
    void setUp() {
        lenient().when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
    }

    @Test
    void testGetExaminations() {
        Exam exam1 = new Exam();
        Exam exam2 = new Exam();
        when(examRepository.findAll()).thenReturn(Arrays.asList(exam1, exam2));

        List<ExamDTO> result = examService.getExaminations();
        assertEquals(2, result.size());
    }

    @Test
    void testGetExaminationFound() {
        Exam exam = new Exam();
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(modelMapper.map(exam, ExamDTO.class)).thenReturn(new ExamDTO());

        ExamDTO result = examService.getExamination(1L);
        assertNotNull(result);
    }

    @Test
    void testCreateExamination() {
        CreateExamDTO createExamDTO = new CreateExamDTO();
        createExamDTO.setDate(LocalDate.now());
        createExamDTO.setDoctorId(1L);
        createExamDTO.setPatientId(1L);

        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Exam savedExam = new Exam();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(examRepository.save(any(Exam.class))).thenReturn(savedExam);
        when(modelMapper.map(savedExam, CreateExamDTO.class)).thenReturn(createExamDTO);

        CreateExamDTO result = examService.createExamination(createExamDTO);
        assertNotNull(result);
    }

//    @Test
//    void testUpdateExamination() {
//        long id = 1L;
//        Exam existingExam = new Exam();
//        existingExam.setDoctor(mock(Doctor.class));
//        existingExam.setPatient(mock(Patient.class));
//        existingExam.setDiagnosis(mock(Diagnosis.class));
//
//        when(examRepository.findById(id)).thenReturn(Optional.of(existingExam));
//
//        UpdateExamDTO updateDTO = new UpdateExamDTO();
//        updateDTO.setDate(LocalDate.now());
//        updateDTO.setDoctorId(1L);
//        updateDTO.setPatientId(1L);
//        updateDTO.setDiagnosisId(1L);
//
//        Doctor updatedDoctor = mock(Doctor.class);
//        when(updatedDoctor.getId()).thenReturn(1L);
//        Patient updatedPatient = mock(Patient.class);
//        when(updatedPatient.getId()).thenReturn(1L);
//        Diagnosis updatedDiagnosis = mock(Diagnosis.class);
//        when(updatedDiagnosis.getId()).thenReturn(1L);
//
//        when(doctorRepository.findById(1L)).thenReturn(Optional.of(updatedDoctor));
//        when(patientRepository.findById(1L)).thenReturn(Optional.of(updatedPatient));
//        when(diagnosisRepository.findById(1L)).thenReturn(Optional.of(updatedDiagnosis));
//
//        Exam updatedExam = new Exam();
//        updatedExam.setDoctor(updatedDoctor);
//        updatedExam.setPatient(updatedPatient);
//        updatedExam.setDiagnosis(updatedDiagnosis);
//
//        when(examRepository.save(existingExam)).thenReturn(updatedExam);
//        when(modelMapper.map(updatedExam, UpdateExamDTO.class)).thenReturn(updateDTO);
//
//        UpdateExamDTO result = examService.updateExamination(updateDTO, id);
//
//        assertNotNull(result);
//        assertEquals(updateDTO.getDate(), result.getDate());
//    }

    @Test
    void testDeleteExamination() {
        long id = 1L;
        doNothing().when(examRepository).deleteById(id);

        examService.deleteExamination(id);

        verify(examRepository, times(1)).deleteById(id);
    }
}
