package personal.GesundKlinik.modules.appointment.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.shared.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TimeBeforeAppointmentValidator implements IAppointmentValidator {

    @Override
    public void validate(Appointment entity) {

        var appointmentDate = entity.getDate();
        var now = LocalDateTime.now();
        var timeGapMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (timeGapMinutes < 30){
            throw new ValidationException("The appointment must bu scheduled at least 30 minutes in advance.");
        }

    }
}
