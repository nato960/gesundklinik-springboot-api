package personal.GesundKlinik.modules.appointment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.modules.appointment.entity.CancellationReason;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentCancellationResponse(@JsonProperty("id")
                                              Long id,
                                              @JsonProperty("doctor_id")
                                              Long idDoctor,
                                              @JsonProperty("pacient_id")
                                              Long idPacient,
                                              @JsonProperty("date")
                                              @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
                                              LocalDateTime date,
                                              @JsonProperty("speciality")
                                              Speciality speciality,
                                              @JsonProperty("cancellation_reason")
                                              CancellationReason cancellationReason
) {
}
