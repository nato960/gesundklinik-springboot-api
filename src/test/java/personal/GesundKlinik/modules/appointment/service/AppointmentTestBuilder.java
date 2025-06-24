package personal.GesundKlinik.modules.appointment.service;

import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.entity.CancellationReason;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.modules.patient.entity.Patient;

import java.time.LocalDateTime;

public class AppointmentTestBuilder {

    private Long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime date = LocalDateTime.now().plusDays(1);
    private Speciality speciality;
    private CancellationReason cancellationReason;

    public static AppointmentTestBuilder builder() {
        return new AppointmentTestBuilder();
    }

    public AppointmentTestBuilder from(Appointment existing) {
        this.id = existing.getId();
        this.doctor = existing.getDoctor();
        this.patient = existing.getPatient();
        this.date = existing.getDate();
        this.speciality = existing.getSpeciality();
        this.cancellationReason = existing.getCancellationReason();
        return this;
    }

    public AppointmentTestBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AppointmentTestBuilder withNoId() {
        this.id = null;
        return this;
    }

    public AppointmentTestBuilder withDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public AppointmentTestBuilder withoutDoctor() {
        this.doctor = null;
        return this;
    }

    public AppointmentTestBuilder withPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public AppointmentTestBuilder withoutPatient() {
        this.patient = null;
        return this;
    }

    public AppointmentTestBuilder withDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public AppointmentTestBuilder withFutureDate() {
        this.date = LocalDateTime.now().plusDays(1);
        return this;
    }

    public AppointmentTestBuilder withPastDate() {
        this.date = LocalDateTime.now().minusDays(1);
        return this;
    }

    public AppointmentTestBuilder withSpeciality(Speciality speciality) {
        this.speciality = speciality;
        return this;
    }

    public AppointmentTestBuilder withoutSpeciality() {
        this.speciality = null;
        return this;
    }

    public AppointmentTestBuilder withCancellationReason(CancellationReason reason) {
        this.cancellationReason = reason;
        return this;
    }

    public AppointmentTestBuilder alreadyCancelled() {
        this.cancellationReason = CancellationReason.OTHER;
        return this;
    }

    public Appointment build() {
        Appointment appointment = new Appointment();

        appointment.setId(id);
        appointment.setDate(date);
        appointment.setSpeciality(speciality);

        if (doctor != null) {
            appointment.assignDoctor(doctor);
        }

        if (patient != null) {
            appointment.assignPatient(patient);
        }

        if (cancellationReason != null) {
            appointment.cancelWithReason(cancellationReason);
        }

        return appointment;
    }
}

