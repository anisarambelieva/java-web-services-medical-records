package com.cscb869_medical_records.web.api;

import com.cscb869_medical_records.dto.sickleave.CreateSickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.SickLeaveDTO;
import com.cscb869_medical_records.dto.sickleave.UpdateSickLeaveDTO;
import com.cscb869_medical_records.service.SickLeaveService;
import com.cscb869_medical_records.util.MapperUtil;
import com.cscb869_medical_records.web.view.controller.model.sickleave.CreateSickLeaveViewModel;
import com.cscb869_medical_records.web.view.controller.model.sickleave.UpdateSickLeaveViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sick-leaves")
public class SickLeaveApiController {

    private final SickLeaveService sickLeaveService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public List<SickLeaveDTO> getSickLeaves() {
        return this.sickLeaveService.getAllSickLeaves();
    }

    @GetMapping("/{id}")
    public SickLeaveDTO getSickLeave(@PathVariable long id) {
        return this.sickLeaveService.getSickLeaveById(id);
    }

    @PostMapping
    public CreateSickLeaveViewModel createSickLeave(@RequestBody CreateSickLeaveViewModel sickLeave) {
        CreateSickLeaveDTO createSickLeaveDTO = mapperUtil.getModelMapper().map(sickLeave, CreateSickLeaveDTO.class);
        CreateSickLeaveDTO createdSickLeave = this.sickLeaveService.createSickLeave(createSickLeaveDTO);
        return mapperUtil.getModelMapper().map(createdSickLeave, CreateSickLeaveViewModel.class);
    }

    @PutMapping("/{id}")
    public UpdateSickLeaveViewModel updateSickLeave(@RequestBody UpdateSickLeaveViewModel sickLeave, @PathVariable long id) {
        UpdateSickLeaveDTO updateSickLeaveDTO = mapperUtil.getModelMapper().map(sickLeave, UpdateSickLeaveDTO.class);
        UpdateSickLeaveDTO updatedSickLeave = this.sickLeaveService.updateSickLeave(id, updateSickLeaveDTO);
        return mapperUtil.getModelMapper().map(updatedSickLeave, UpdateSickLeaveViewModel.class);
    }

    @DeleteMapping("/{id}")
    public void deleteSickLeave(@PathVariable long id) {
        this.sickLeaveService.deleteSickLeave(id);
    }
}
