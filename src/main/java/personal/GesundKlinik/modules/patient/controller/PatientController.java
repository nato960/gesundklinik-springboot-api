package personal.GesundKlinik.modules.patient.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import personal.GesundKlinik.modules.patient.dto.request.SavePatientRequest;
import personal.GesundKlinik.modules.patient.dto.request.UpdatePatientRequest;
import personal.GesundKlinik.modules.patient.dto.response.*;
import personal.GesundKlinik.modules.patient.mapper.IPatientMapper;
import personal.GesundKlinik.modules.patient.query.IPatientQueryService;
import personal.GesundKlinik.modules.patient.service.IPatientService;

import java.util.List;


@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Patients")
public class PatientController {

    private final IPatientService service;
    private final IPatientQueryService queryService;
    private final IPatientMapper mapper;


    @PostMapping
    public ResponseEntity<SavePatientResponse> registerPatient(
            @RequestBody @Valid final SavePatientRequest request){

        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        var response = mapper.toSaveResponse(savedEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("{id}")
    public ResponseEntity<DetailPatientResponse> getPatientDetails(@PathVariable final Long id){
        var entity = queryService.findById(id);
        var response = mapper.toDetailResponse(entity);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PagePatientResponse>> listPaged(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        var page = queryService.listPaged(pageable);
        var response = page.map(mapper::toPageResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public  ResponseEntity<List<ListPatientResponse>> listAllPatients(){
        var entities = queryService.list();
        var response = mapper.toListResponse(entities);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdatePatientResponse> updatePatient(
            @PathVariable final Long id,
            @RequestBody @Valid final UpdatePatientRequest request){

        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.update(entity);
        var response = mapper.toUpdateResponse(updatedEntity);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}/deactivation")
    public ResponseEntity<Void> deactivatePatient(@PathVariable final Long id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

}
