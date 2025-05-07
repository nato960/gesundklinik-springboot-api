package personal.GesundKlinik.modules.appointment.service;

import jakarta.transaction.Transactional;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.entity.CancellationReason;

public interface IAppointmentService {

    Appointment schedule(final Appointment entity);

    Appointment reschedule(final Appointment entity);

    Appointment cancelAppointment(Appointment entity);
}
