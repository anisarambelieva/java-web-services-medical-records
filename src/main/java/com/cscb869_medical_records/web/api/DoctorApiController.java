package com.cscb869_medical_records.web.api;

import com.cscb869_medical_records.data.entity.Doctor;
import com.cscb869_medical_records.dto.doctor.CreateDoctorDTO;
import com.cscb869_medical_records.dto.doctor.DoctorDTO;
import com.cscb869_medical_records.dto.doctor.UpdateDoctorDTO;
import com.cscb869_medical_records.service.DoctorService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.doctor.CreateDoctorViewModel;
import com.cscb869_medical_records.web.view.controller.model.doctor.DoctorViewModel;
import com.cscb869_medical_records.web.view.controller.model.doctor.UpdateDoctorViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorApiController {

    private final DoctorService doctorService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public List<DoctorDTO> getDoctors() { return this.doctorService.getDoctors(); }

    @GetMapping("/{id}")
    public DoctorViewModel getDoctor(@PathVariable long id) {
        return mapperUtil.getModelMapper().map(this.doctorService.getDoctor(id), DoctorViewModel.class);
    }

    @PostMapping
    public CreateDoctorViewModel createDoctor(@RequestBody CreateDoctorViewModel doctor) {
        return mapperUtil.getModelMapper().map(this.doctorService
                .createDoctor(mapperUtil.getModelMapper().map(doctor, CreateDoctorDTO.class)), CreateDoctorViewModel.class);
    }

    @PutMapping("/{id}")
    public UpdateDoctorViewModel updateDoctor(@RequestBody Doctor doctor, @PathVariable long id) {
        return mapperUtil.getModelMapper().map(this.doctorService.
                updateDoctor(mapperUtil.getModelMapper().map(doctor, UpdateDoctorDTO.class), id), UpdateDoctorViewModel.class);
    }

    @DeleteMapping("/{id}")
    public void DeleteDoctor(@PathVariable long id) { this.doctorService.deleteDoctor(id); }
}
