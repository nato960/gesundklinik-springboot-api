package personal.GesundKlinik.modules.appointment.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.modules.appointment.repository.IAppointmentRepository;
import personal.GesundKlinik.modules.appointment.validation.IAppointmentValidator;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.patient.query.IPatientQueryService;
import personal.GesundKlinik.shared.exception.InvalidPatientIdException;
import personal.GesundKlinik.shared.exception.NoDoctorAvailableOnThisDateException;
import personal.GesundKlinik.shared.exception.NoSpecialityChosenException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService{

    private final IAppointmentRepository appointmentRepository;
    private final IDoctorQueryService doctorQueryService;
    private final IPatientQueryService patientQueryService;
    private final IAppointmentQueryService appointmentQueryService;
    private final List<IAppointmentValidator> appointmentValidators;

    @Transactional
    @Override
    public Appointment schedule(Appointment entity) {

        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            appointmentQueryService.verifyDoctorExists(entity.getDoctor().getId());
        }

        if (entity.getPatient() == null || entity.getPatient().getId() == null) {
            throw new InvalidPatientIdException("A patient must be provided with a valid ID.");
        }
        appointmentQueryService.verifyPatientExists(entity.getPatient().getId());

        var patient = patientQueryService.getReferenceById(entity.getPatient().getId());

        entity.assignPatient(patient);
        assignDoctorIfNeeded(entity);

        appointmentValidators.forEach(v -> v.validate(entity));

        return appointmentRepository.save(entity);
    }

    @Transactional
    @Override
    public Appointment reschedule(Appointment entityToUpdate) {

        var stored = appointmentQueryService.findById(entityToUpdate.getId());

        if (entityToUpdate.getPatient() != null && !entityToUpdate.getPatient().getId().equals(stored.getPatient().getId())) {
            throw new InvalidPatientIdException("The patient of an appointment cannot be changed.");
        }

        Doctor doctor = null;
        if (entityToUpdate.getDoctor() != null && entityToUpdate.getDoctor().getId() != null){
            appointmentQueryService.verifyDoctorExists(entityToUpdate.getDoctor().getId());
            doctor = doctorQueryService.getReferenceById(entityToUpdate.getDoctor().getId());
        }

        stored.updateWith(doctor, entityToUpdate.getDate());

        appointmentValidators.forEach(v -> v.validate(stored));

        return appointmentRepository.save(stored);
    }

    @Transactional
    @Override
    public Appointment cancelAppointment(Appointment entity) {
        var existingAppointment = appointmentQueryService.findById(entity.getId());

        existingAppointment.cancelWithReason(entity.getCancellationReason());

        return appointmentRepository.save(existingAppointment);
    }


    private void assignDoctorIfNeeded(Appointment entity) {
        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            var doctor = doctorQueryService.getReferenceById(entity.getDoctor().getId());
            entity.assignDoctor(doctor);
            return;
        }

        if (entity.getSpeciality() == null) {
            throw new NoSpecialityChosenException("Speciality must be chosen when there is no chosen doctor.");
        }

        var chosen = doctorQueryService.chooseRandomDoctorFreeOnDate(entity.getSpeciality(), entity.getDate());
        if (chosen == null) {
            throw new NoDoctorAvailableOnThisDateException("There is no doctor available on the chosen date.");
        }

        entity.assignDoctor(chosen);
    }

}

