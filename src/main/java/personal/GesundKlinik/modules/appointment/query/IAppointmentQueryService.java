package personal.GesundKlinik.modules.appointment.query;

import personal.GesundKlinik.modules.appointment.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentQueryService {


    void verifyDoctorExists(final Long id);

    void verifyPacientExists(final Long id);

    Appointment findById(final Long id);

    List<Appointment> list();

    boolean existsByDoctorIdAndDateAndCancellationReasonIsNull(Long idDoctor, LocalDateTime date);

    boolean existsByPacientIdAndDateBetween(Long idPacient, LocalDateTime firstHour, LocalDateTime lastHour);

    boolean isDoctorActive(Long id);

    boolean isPacientActive(Long id);

    boolean hasDoctorAppointmentAt(Long idDoctor, LocalDateTime date);

    boolean hasPacientAppointmentOnDate(Long idPacient, LocalDateTime start, LocalDateTime end);




}
