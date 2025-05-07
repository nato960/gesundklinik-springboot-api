package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.shared.exception.ValidationException;

@Component
@RequiredArgsConstructor
public class DoctorWithAnotherAppointmentAtTheSameTimeValidator implements IAppointmentValidator {

    private final IAppointmentQueryService queryService;

    @Override
    public void validate(Appointment entity) {

        boolean doctorHasAnotherAppointment;

        if (entity.getId() == null) {
            // schedule
            doctorHasAnotherAppointment = queryService.existsByDoctorIdAndDateAndCancellationReasonIsNull(
                    entity.getDoctor().getId(),
                    entity.getDate()
            );
        } else {
            // reschedule
            doctorHasAnotherAppointment = queryService.existsByDoctorIdAndDateAndCancellationReasonIsNullAndIdNot(
                    entity.getDoctor().getId(),
                    entity.getDate(),
                    entity.getId()
            );
        }

        if (doctorHasAnotherAppointment) {
            throw new ValidationException("Doctor already has an appointment at this time.");
        }
    }
}