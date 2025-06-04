package personal.GesundKlinik.modules.appointment.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.repository.IAppointmentRepository;
import personal.GesundKlinik.modules.doctor.repository.IDoctorRepository;
import personal.GesundKlinik.modules.patient.repository.IPatientRepository;
import personal.GesundKlinik.shared.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmenteQueryService implements IAppointmentQueryService{

    private final IAppointmentRepository appointmentRepository;
    private final IDoctorRepository doctorRepository;
    private final IPatientRepository pacientRepository;

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    public List<Appointment> list(){
        return appointmentRepository.findAll();
    }

    @Override
    public void verifyPatientExists(Long id) {
        if (!pacientRepository.existsById(id)){
            throw new NotFoundException("ID " + id + " not found");
        }
    }

    @Override
    public void verifyDoctorExists(Long id) {
        if (!doctorRepository.existsById(id)){
            throw new NotFoundException("ID " + id + " not found");
        }
    }

    @Override
    public boolean existsByDoctorIdAndDateAndCancellationReasonIsNull(Long idDoctor, LocalDateTime date) {
        return appointmentRepository.existsByDoctorIdAndDateAndCancellationReasonIsNull(idDoctor, date);
    }

    @Override
    public boolean existsByDoctorIdAndDateAndCancellationReasonIsNullAndIdNot(Long doctorId, LocalDateTime date, Long idAppointment) {
        return appointmentRepository.existsByDoctorIdAndDateAndCancellationReasonIsNullAndIdNot(doctorId, date, idAppointment);
    }

    @Override
    public boolean existsByPatientIdAndDateBetweenAndIdNot(Long idPatient, LocalDateTime openAt, LocalDateTime closedAt, Long idAppointment) {
        return appointmentRepository.existsByPatientIdAndDateBetweenAndIdNot(idPatient, openAt, closedAt, idAppointment);
    }

    @Override
    public boolean existsByPatientIdAndDateBetween(Long idPacient, LocalDateTime firstHour, LocalDateTime lastHour) {
        return appointmentRepository.existsByPatientIdAndDateBetween(idPacient, firstHour, lastHour);
    }

    @Override
    public boolean isDoctorActive(Long id) {
        return doctorRepository.findActiveById(id);
    }

    @Override
    public boolean isPatientActive(Long id) {
        return pacientRepository.findActiveById(id);
    }


}
