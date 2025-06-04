package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.shared.exception.ValidationException;

@Component
@RequiredArgsConstructor
public class ActivePatientValidator implements IAppointmentValidator {

    private final IAppointmentQueryService queryService;

    @Override
    public void validate(Appointment entity) {

        if (entity.getPatient().getId() == null){
            return;
        }

        var activePatient = queryService.isPatientActive(entity.getPatient().getId());
        if (!activePatient){
            throw new ValidationException("An appointment can not be schedule without an active patient.");
        }
    }
}
