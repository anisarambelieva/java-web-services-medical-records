package com.cscb869_medical_records.service;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.data.repo.DoctorRepository;
import com.cscb869_medical_records.dto.doctor.CreateDoctorDTO;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.service.Impl.DoctorServiceImpl;
import com.cscb869_medical_records.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @BeforeEach
    void setUp() {
        lenient().when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
    }

    private Doctor createDoctor(String name, Long id) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        if (id != null) {
            ReflectionTestUtils.setField(doctor, "id", id);
        }
        return doctor;
    }

    @Test
    void testGetDoctors() {
        Doctor doctor1 = createDoctor("Dr. Smith", 1L);
        Doctor doctor2 = createDoctor("Dr. Johnson", 2L);
        List<Doctor> doctorList = Arrays.asList(doctor1, doctor2);
        when(doctorRepository.findAll()).thenReturn(doctorList);
        when(mapperUtil.mapList(doctorList, DoctorDTO.class)).thenReturn(Arrays.asList(new DoctorDTO(), new DoctorDTO()));

        List<DoctorDTO> result = doctorService.getDoctors();

        assertEquals(2, result.size());
    }

    @Test
    void testGetDoctorFound() {
        Doctor doctor = createDoctor("Dr. Smith", 1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(new DoctorDTO());

        DoctorDTO result = doctorService.getDoctor(1L);

        assertNotNull(result);
    }

    @Test
    void testGetDoctorNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> doctorService.getDoctor(1L));
        assertTrue(exception.getMessage().contains("Doctor with id=1 not found!"));
    }

    @Test
    void testCreateDoctor() {
        CreateDoctorDTO createDoctorDTO = new CreateDoctorDTO();
        Doctor doctor = createDoctor("Dr. Smith", null);
        Doctor savedDoctor = createDoctor("Dr. Smith", 1L);
        when(modelMapper.map(createDoctorDTO, Doctor.class)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(savedDoctor);
        when(modelMapper.map(savedDoctor, CreateDoctorDTO.class)).thenReturn(createDoctorDTO);

        CreateDoctorDTO result = doctorService.createDoctor(createDoctorDTO);

        assertNotNull(result);
    }

    @Test
    void testUpdateDoctor() {
        long id = 1L;
        Doctor existingDoctor = createDoctor("Dr. Old", id);
        when(doctorRepository.findById(id)).thenReturn(Optional.of(existingDoctor));

        UpdateDoctorDTO updateDTO = new UpdateDoctorDTO();
        updateDTO.setName("Dr. New");
        updateDTO.setSpecialty(null);

        Doctor updatedDoctor = createDoctor("Dr. New", id);
        when(doctorRepository.save(existingDoctor)).thenReturn(updatedDoctor);
        when(modelMapper.map(updatedDoctor, UpdateDoctorDTO.class)).thenReturn(updateDTO);

        UpdateDoctorDTO result = doctorService.updateDoctor(updateDTO, id);

        assertNotNull(result);
        assertEquals("Dr. New", result.getName());
    }

    @Test
    void testDeleteDoctor() {
        long id = 1L;
        doNothing().when(doctorRepository).deleteById(id);

        doctorService.deleteDoctor(id);

        verify(doctorRepository, times(1)).deleteById(id);
    }
}
