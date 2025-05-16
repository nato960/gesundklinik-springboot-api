package personal.GesundKlinik.modules.appointment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import personal.GesundKlinik.modules.appointment.dto.request.AppointmentCancellationRequest;
import personal.GesundKlinik.modules.appointment.dto.request.AppointmentRescheduleRequest;
import personal.GesundKlinik.modules.appointment.dto.response.AppointmentCancellationResponse;
import personal.GesundKlinik.modules.appointment.dto.response.AppointmentRescheduleResponse;
import personal.GesundKlinik.modules.appointment.dto.response.AppointmentScheduledResponse;
import personal.GesundKlinik.modules.appointment.dto.request.AppointmentScheduleRequest;
import personal.GesundKlinik.modules.appointment.dto.response.DetailsAppointmentResponse;
import personal.GesundKlinik.modules.appointment.mapper.IAppointmentMapper;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.modules.appointment.service.IAppointmentService;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    private final IAppointmentService service;
    private final IAppointmentQueryService queryService;
    private final IAppointmentMapper mapper;


    @PostMapping
    @ResponseStatus(CREATED)
    public AppointmentScheduledResponse save(@RequestBody @Valid final AppointmentScheduleRequest request){
        var entity = mapper.toEntity(request);
        var savedEntity = service.schedule(entity);
        return mapper.toSaveResponse(savedEntity);
    }

    @GetMapping("{id}")
    public DetailsAppointmentResponse findById(@PathVariable final Long id){
        var entity = queryService.findById(id);
        return mapper.toDetailsResponse(entity);
    }

    @PutMapping("{id}")
    public AppointmentRescheduleResponse update(@PathVariable final Long id, @RequestBody @Valid final AppointmentRescheduleRequest request){
        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.reschedule(entity);
        return mapper.toRescheduleResponse(updatedEntity);
    }

    @PutMapping("{id}/cancellation")
    public AppointmentCancellationResponse cancel(@PathVariable final Long id, @RequestBody @Valid final AppointmentCancellationRequest request){
        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.cancelAppointment(entity);
        return mapper.toCancellationResponse(updatedEntity);
    }

}
