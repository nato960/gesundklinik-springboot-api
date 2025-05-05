package personal.GesundKlinik.modules.appointment.service;

import personal.GesundKlinik.modules.appointment.entity.Appointment;

public interface IAppointmentService {

    Appointment schedule(final Appointment entity);

    Appointment reschedule(final Appointment entity);

    void cancelAppointment(final Long id);



}
