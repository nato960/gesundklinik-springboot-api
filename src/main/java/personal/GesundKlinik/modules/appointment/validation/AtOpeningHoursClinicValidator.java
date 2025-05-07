package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.shared.exception.ValidationException;

import java.time.DayOfWeek;

@Component
@RequiredArgsConstructor
public class AtOpeningHoursClinicValidator implements IAppointmentValidator {

    @Override
    public void validate(Appointment entity) {

        var appointmentDate = entity.getDate();

        boolean isSunday = appointmentDate.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean isBeforeOpening = appointmentDate.getHour() < 7;
        boolean isAfterClosing = appointmentDate.getHour() > 18;

        if (isSunday || isBeforeOpening || isAfterClosing) {
            throw new ValidationException("Consultations are allowed only from Monday to Saturday, between 07:00 and 18:00.");
        }

    }
}
