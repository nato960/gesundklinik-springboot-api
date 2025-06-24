package personal.GesundKlinik.modules.doctor.controller;

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
import personal.GesundKlinik.modules.doctor.dto.request.SaveDoctorRequest;
import personal.GesundKlinik.modules.doctor.dto.request.UpdateDoctorRequest;
import personal.GesundKlinik.modules.doctor.dto.response.*;
import personal.GesundKlinik.modules.doctor.mapper.IDoctorMapper;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.doctor.service.IDoctorService;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Doctors")
public class DoctorController {

    private final IDoctorService service;
    private final IDoctorQueryService queryService;
    private final IDoctorMapper mapper;


    @PostMapping
    public ResponseEntity<SaveDoctorResponse> registerDoctor(@RequestBody @Valid final SaveDoctorRequest request){
        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        var response = mapper.toSaveResponse(savedEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailDoctorResponse> getDoctorDetails(@PathVariable final Long id){
        var entity = queryService.findById(id);
        var response = mapper.toDetailResponse(entity);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PageDoctorResponse>> listDoctorsPaged(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        var page = queryService.listPaged(pageable);
        var response = page.map(mapper::toPageResponse);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<ListDoctorResponse>> listAllDoctors(){
        var entities = queryService.list();
        var response = mapper.toListResponse(entities);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateDoctorResponse> updateDoctor(
            @PathVariable final Long id,
            @RequestBody @Valid final UpdateDoctorRequest request){

        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.update(entity);
        var response = mapper.toUpdateResponse(updatedEntity);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}/deactivation")
    public ResponseEntity<Void> deactivateDoctor(@PathVariable final Long id){
        service.softDelete(id);
        return ResponseEntity.noContent().build();

    }

}

