package personal.GesundKlinik.modules.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.GesundKlinik.modules.appointment.entity.Appointment;

import java.time.LocalDateTime;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndDateAndCancellationReasonIsNull(Long idDoctor, LocalDateTime date);

    boolean existsByDoctorIdAndDateAndCancellationReasonIsNullAndIdNot(Long doctorId, LocalDateTime date, Long idAppointment);

    boolean existsByPacientIdAndDateBetween(Long idPacient, LocalDateTime firstHour, LocalDateTime lastHour);

    boolean existsByPacientIdAndDateBetweenAndIdNot(Long idPacient, LocalDateTime openAt, LocalDateTime closedAt, Long id);



}