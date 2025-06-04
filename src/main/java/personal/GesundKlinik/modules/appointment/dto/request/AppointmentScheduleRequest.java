package personal.GesundKlinik.modules.appointment.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentScheduleRequest(@JsonProperty("idDoctor")
                                         Long idDoctor,
                                         @NotNull
                                         @JsonProperty("idPatient")
                                         Long idPatient,
                                         @NotNull
                                         @Future
                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
                                         @JsonProperty("date")
                                         LocalDateTime date,
                                         @JsonProperty("speciality")
                                         Speciality speciality) {

    @AssertTrue(message = "Either doctor ID or speciality must be provided")
    public boolean isEitherDoctorOrSpecialityProvided() {
        return idDoctor != null || speciality != null;
    }

}
