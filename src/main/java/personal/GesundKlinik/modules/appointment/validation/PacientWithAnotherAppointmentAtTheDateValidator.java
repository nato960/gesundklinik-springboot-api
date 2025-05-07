package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.shared.exception.ValidationException;

@Component
@RequiredArgsConstructor
public class PacientWithAnotherAppointmentAtTheDateValidator implements IAppointmentValidator {

    private final IAppointmentQueryService queryService;

    @Override
    public void validate(Appointment entity) {
        var openAt = entity.getDate().withHour(7);
        var closedAt = entity.getDate().withHour(18);

        boolean exists;
        if (entity.getId() == null) {
            // schedule
            exists = queryService.existsByPacientIdAndDateBetween(entity.getPacient().getId(), openAt, closedAt);
        } else {
            // reschedule
            exists = queryService.existsByPacientIdAndDateBetweenAndIdNot(entity.getPacient().getId(), openAt, closedAt, entity.getId());
        }

        if (exists) {
            throw new ValidationException("The pacient already has an appointment on the date.");
        }
    }
}
