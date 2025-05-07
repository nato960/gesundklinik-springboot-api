package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.shared.exception.ValidationException;

@Component
@RequiredArgsConstructor
public class ActiveDoctorValidator implements IAppointmentValidator {

    private final IAppointmentQueryService queryService;

    @Override
    public void validate(Appointment entity) {

        if (entity.getDoctor().getId() == null){
            return;
        }

        var activeDoctor = queryService.isDoctorActive(entity.getDoctor().getId());

        if (!activeDoctor){
            throw new ValidationException("An appointment can not be schedule without an active doctor.");
        }

    }
}
