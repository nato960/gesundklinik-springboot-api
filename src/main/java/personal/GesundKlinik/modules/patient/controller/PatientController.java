package personal.GesundKlinik.modules.patient.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import personal.GesundKlinik.modules.patient.dto.request.SavePatientRequest;
import personal.GesundKlinik.modules.patient.dto.request.UpdatePatientRequest;
import personal.GesundKlinik.modules.patient.dto.response.*;
import personal.GesundKlinik.modules.patient.mapper.IPatientMapper;
import personal.GesundKlinik.modules.patient.query.IPatientQueryService;
import personal.GesundKlinik.modules.patient.service.IPatientService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("/patients")
@Tag(name = "Patients")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    private final IPatientService service;
    private final IPatientQueryService queryService;
    private final IPatientMapper mapper;


    @PostMapping
    @ResponseStatus(CREATED)
    public SavePatientResponse save(@RequestBody @Valid final SavePatientRequest request){
        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        return mapper.toSaveResponse(savedEntity);
    }


    @GetMapping("{id}")
    public DetailPatientResponse findById(@PathVariable final Long id){
        var entity = queryService.findById(id);
        return mapper.toDetailResponse(entity);
    }

    @GetMapping("/page")
    public Page<PagePatientResponse> listPaged(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        var page = queryService.listPaged(pageable);
        return page.map(mapper::toPageResponse);
    }

    @GetMapping
    public List<ListPatientResponse> listAll(){
        var entities = queryService.list();
        return mapper.toListResponse(entities);
    }

    @PutMapping("{id}")
    public UpdatePatientResponse update(@PathVariable final Long id, @RequestBody @Valid final UpdatePatientRequest request){
        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.update(entity);
        return mapper.toUpdateResponse(updatedEntity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final Long id){
        service.softDelete(id);
    }

}
