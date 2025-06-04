package personal.GesundKlinik.modules.appointment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import personal.GesundKlinik.modules.appointment.dto.request.AppointmentCancellationRequest;
import personal.GesundKlinik.modules.appointment.dto.request.AppointmentRescheduleRequest;
import personal.GesundKlinik.modules.appointment.dto.request.AppointmentScheduleRequest;
import personal.GesundKlinik.modules.appointment.dto.response.AppointmentCancellationResponse;
import personal.GesundKlinik.modules.appointment.dto.response.AppointmentRescheduleResponse;
import personal.GesundKlinik.modules.appointment.dto.response.AppointmentScheduledResponse;
import personal.GesundKlinik.modules.appointment.dto.response.DetailsAppointmentResponse;
import personal.GesundKlinik.modules.appointment.entity.Appointment;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IAppointmentMapper {

    // Schedule
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor.id", source = "idDoctor")
    @Mapping(target = "patient.id", source = "idPatient")
    Appointment toEntity(final AppointmentScheduleRequest request);

    @Mapping(target = "idDoctor", source = "doctor.id")
    @Mapping(target = "idPatient", source = "patient.id")
    AppointmentScheduledResponse toSaveResponse(final Appointment entity);

    // Details
    @Mapping(target = "idDoctor", source = "doctor.id")
    @Mapping(target = "idPatient", source = "patient.id")
    DetailsAppointmentResponse toDetailsResponse(final Appointment entity);

    // Reschedule
    @Mapping(target = "id", source = "id")
    @Mapping(target = "doctor.id", source = "request.idDoctor")
    Appointment toEntity(final Long id, final AppointmentRescheduleRequest request);

    @Mapping(target = "idDoctor", source = "doctor.id")
    @Mapping(target = "idPatient", source = "patient.id")
    AppointmentRescheduleResponse toRescheduleResponse(final Appointment entity);

    // Cancel
    @Mapping(target = "id", source = "id")
    //@Mapping(target = "doctor.id", source = "request.idDoctor")
    Appointment toEntity(final Long id, final AppointmentCancellationRequest request);

    @Mapping(target = "idDoctor", source = "doctor.id")
    @Mapping(target = "idPatient", source = "patient.id")
    AppointmentCancellationResponse toCancellationResponse(final Appointment entity);

}

