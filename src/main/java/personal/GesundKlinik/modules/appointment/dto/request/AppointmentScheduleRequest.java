package personal.GesundKlinik.modules.appointment.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentScheduleRequest(
                                         Long idDoctor,
                                         @NotNull
                                         Long idPacient,
                                         @NotNull
                                         @Future
                                         LocalDateTime date,
                                         Speciality speciality) {

    @AssertTrue(message = "Speciality must be provided when no doctor is selected")
    public boolean isSpecialityRequiredIfNoDoctor() {
        return idDoctor != null || speciality != null;
    }

}
