package personal.GesundKlinik.modules.appointment.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentCreateDto(
        @NotNull
        Long idDoctor,
        @NotNull
        Long idPacient,
        @NotNull
        @Future
        LocalDateTime date,
        @NotNull
        Speciality speciality) {
}
