package personal.GesundKlinik.modules.appointment.dto;

import personal.GesundKlinik.modules.appointment.entity.Appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsDto(Long id, Long idDoctor, Long idPacient, LocalDateTime date) {

    public AppointmentDetailsDto(Appointment appointment){
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPacient().getId(), appointment.getDate());
    }
}
