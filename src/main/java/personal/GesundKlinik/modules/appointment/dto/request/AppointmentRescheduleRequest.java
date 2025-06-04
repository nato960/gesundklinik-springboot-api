package personal.GesundKlinik.modules.appointment.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentRescheduleRequest(
        @JsonProperty("id_doctor")
        Long idDoctor,
        @JsonProperty("id_patient")
        Long idPatient,
        @JsonProperty("date")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime date,
        @JsonProperty("speciality")
        Speciality speciality
)  {
}
