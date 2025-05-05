package personal.GesundKlinik.modules.appointment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.modules.appointment.repository.IAppointmentRepository;
import personal.GesundKlinik.modules.appointment.validation.IAppointmentValidator;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.pacient.query.IPacientQueryService;
import personal.GesundKlinik.shared.exception.MissingPacientException;
import personal.GesundKlinik.shared.exception.NoDoctorAvailableOnThisDateException;
import personal.GesundKlinik.shared.exception.NoSpecialityChosenException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService implements IAppointmentService{

    private final IAppointmentRepository appointmentRepository;
    private final IDoctorQueryService doctorQueryService;
    private final IPacientQueryService pacientQueryService;
    private final IAppointmentQueryService queryService;
    private final List<IAppointmentValidator> appointmentScheduleValidators;

    @Transactional
    @Override
    public Appointment schedule(Appointment entity) {

        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            queryService.verifyDoctorExists(entity.getDoctor().getId());
        }

        if (entity.getPacient() == null || entity.getPacient().getId() == null) {
            throw new MissingPacientException("A patient must be provided with a valid ID.");
        }
        queryService.verifyPacientExists(entity.getPacient().getId());

        appointmentScheduleValidators.forEach(v -> v.validate(entity));

        var pacient = pacientQueryService.getReferenceById(entity.getPacient().getId());
        entity.setPacient(pacient);

        assignDoctorIfNeeded(entity);

        return appointmentRepository.save(entity);
    }

    @Transactional
    @Override
    public Appointment reschedule(Appointment entity) {

//        queryService.verifyEmail(entity.getId(), entity.getEmail());
//        queryService.verifyPhone(entity.getId(), entity.getPhone());
//
//        var stored = queryService.findById(entity.getId());
//        if (entity.getName() != null)
//            stored.setName(entity.getName());
//
//        if (entity.getEmail() != null)
//            stored.setEmail(entity.getEmail());
//
//        if (entity.getPhone() != null)
//            stored.setPhone(entity.getPhone());
//
//        if (entity.getAddress() != null)
//            stored.setAddress(entity.getAddress());
//
//        return repository.save(stored);

        return null;
    }

    @Transactional
    @Override
    public void cancelAppointment(Long id) {

    }

    private void assignDoctorIfNeeded(Appointment entity) {
        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            var doctor = doctorQueryService.getReferenceById(entity.getDoctor().getId());
            entity.setDoctor(doctor);
            return;
        }

        if (entity.getSpeciality() == null) {
            throw new NoSpecialityChosenException("Speciality must be chosen when there is no chosen doctor.");
        }

        var chosen = doctorQueryService.chooseRandomDoctorFreeOnDate(entity.getSpeciality(), entity.getDate());
        if (chosen == null) {
            throw new NoDoctorAvailableOnThisDateException("There is no doctor available on the chosen date.");
        }

        entity.setDoctor(chosen);
    }

}

