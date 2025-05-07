package personal.GesundKlinik.modules.appointment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentRescheduleResponse(
        @JsonProperty("id_appointment")
        Long id,
        @JsonProperty("id_doctor")
        Long idDoctor,
        @JsonProperty("id_pacient")
        Long idPacient,
        @JsonProperty("date")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime date,
        @JsonProperty("speciality")
        Speciality speciality
) {
}
