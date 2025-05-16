package personal.GesundKlinik.modules.doctor.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import personal.GesundKlinik.modules.doctor.dto.request.SaveDoctorRequest;
import personal.GesundKlinik.modules.doctor.dto.request.UpdateDoctorRequest;
import personal.GesundKlinik.modules.doctor.dto.response.*;
import personal.GesundKlinik.modules.doctor.mapper.IDoctorMapper;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.doctor.service.IDoctorService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    private final IDoctorService service;
    private final IDoctorQueryService queryService;
    private final IDoctorMapper mapper;


    @PostMapping
    @ResponseStatus(CREATED)
    public SaveDoctorResponse save(@RequestBody @Valid final SaveDoctorRequest request){
        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        return mapper.toSaveResponse(savedEntity);
    }

    @GetMapping("{id}")
    public DetailDoctorResponse findById(@PathVariable final Long id){
        var entity = queryService.findById(id);
        return mapper.toDetailResponse(entity);
    }

    @GetMapping("/page")
    public Page<PageDoctorResponse> listPaged(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        var page = queryService.listPaged(pageable);
        return page.map(mapper::toPageResponse);
    }

    @GetMapping
    public List<ListDoctorResponse> listAll(){
        var entities = queryService.list();
        return mapper.toListResponse(entities);
    }

    @PutMapping("{id}")
    public UpdateDoctorResponse update(@PathVariable final Long id, @RequestBody @Valid final UpdateDoctorRequest request){
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

