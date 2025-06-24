package personal.GesundKlinik.modules.appointment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Appointments")
public class AppointmentController {

    private final IAppointmentService service;
    private final IAppointmentQueryService queryService;
    private final IAppointmentMapper mapper;


    @PostMapping
    public ResponseEntity<AppointmentScheduledResponse> scheduleAppointment(
            @RequestBody @Valid final AppointmentScheduleRequest request){

        var entity = mapper.toEntity(request);
        var savedEntity = service.schedule(entity);
        var response = mapper.toSaveResponse(savedEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailsAppointmentResponse> getAppointmentDetails(
            @PathVariable final Long id){

        var entity = queryService.findById(id);
        var response = mapper.toDetailsResponse(entity);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<AppointmentRescheduleResponse> rescheduleAppointment(
            @PathVariable final Long id,
            @RequestBody @Valid final AppointmentRescheduleRequest request){

        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.reschedule(entity);
        var response = mapper.toRescheduleResponse(updatedEntity);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}/cancellation")
    public ResponseEntity<AppointmentCancellationResponse> cancelAppointment(
            @PathVariable final Long id,
            @RequestBody @Valid final AppointmentCancellationRequest request){

        var entity = mapper.toEntity(id, request);
        var updatedEntity = service.cancelAppointment(entity);
        var response = mapper.toCancellationResponse(updatedEntity);

        return ResponseEntity.ok(response);
    }

}
