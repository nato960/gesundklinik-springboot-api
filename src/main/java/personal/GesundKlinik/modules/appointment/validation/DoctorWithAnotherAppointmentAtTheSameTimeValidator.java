package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.shared.exception.ValidationException;

@Component
@RequiredArgsConstructor
public class DoctorWithAnotherAppointmentAtTheSameTimeValidator implements IAppointmentValidator{

    private final IAppointmentQueryService queryService;

    @Override
    public void validate(Appointment entity) {

        var doctorHasAnotherAppointmentAtTheSameTime = queryService.existsByDoctorIdAndDateAndCancellationReasonIsNull(
                entity.getDoctor().getId(),
                entity.getDate());

        if (doctorHasAnotherAppointmentAtTheSameTime){
            throw new ValidationException("Doctor already has an appointment at this time.");
        }

    }
}
