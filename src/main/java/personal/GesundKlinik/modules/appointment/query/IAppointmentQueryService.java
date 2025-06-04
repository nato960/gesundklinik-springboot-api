package personal.GesundKlinik.modules.appointment.query;

import personal.GesundKlinik.modules.appointment.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentQueryService {

    void verifyDoctorExists(final Long id);

    void verifyPatientExists(final Long id);

    Appointment findById(final Long id);

    List<Appointment> list();

    boolean existsByDoctorIdAndDateAndCancellationReasonIsNull(Long idDoctor, LocalDateTime date);

    boolean existsByDoctorIdAndDateAndCancellationReasonIsNullAndIdNot(Long doctorId, LocalDateTime date, Long idAppointment);

    boolean existsByPatientIdAndDateBetween(Long idPatient, LocalDateTime firstHour, LocalDateTime lastHour);

    boolean existsByPatientIdAndDateBetweenAndIdNot(Long idPatient, LocalDateTime openAt, LocalDateTime closedAt, Long idAppointment);

    boolean isDoctorActive(Long id);

    boolean isPatientActive(Long id);




}
