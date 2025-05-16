package personal.GesundKlinik.modules.pacient.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import personal.GesundKlinik.modules.pacient.dto.request.SavePacientRequest;
import personal.GesundKlinik.modules.pacient.dto.request.UpdatePacientRequest;
import personal.GesundKlinik.modules.pacient.dto.response.*;
import personal.GesundKlinik.modules.pacient.mapper.IPacientMapper;
import personal.GesundKlinik.modules.pacient.query.IPacientQueryService;
import personal.GesundKlinik.modules.pacient.service.IPacientService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("/pacients")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class PacientController {

    private final IPacientService service;
    private final IPacientQueryService queryService;
    private final IPacientMapper mapper;


    @PostMapping
    @ResponseStatus(CREATED)
    public SavePacientResponse save(@RequestBody @Valid final SavePacientRequest request){
        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        return mapper.toSaveResponse(savedEntity);
    }


    @GetMapping("{id}")
    public DetailPacientResponse findById(@PathVariable final Long id){
        var entity = queryService.findById(id);
        return mapper.toDetailResponse(entity);
    }

    @GetMapping("/page")
    public Page<PagePacientResponse> listPaged(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        var page = queryService.listPaged(pageable);
        return page.map(mapper::toPageResponse);
    }

    @GetMapping
    public List<ListPacientResponse> listAll(){
        var entities = queryService.list();
        return mapper.toListResponse(entities);
    }

    @PutMapping("{id}")
    public UpdatePacientResponse update(@PathVariable final Long id, @RequestBody @Valid final UpdatePacientRequest request){
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
