package personal.GesundKlinik.modules.appointment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.modules.patient.entity.Patient;
import personal.GesundKlinik.shared.exception.AppointmentAlreadyCancelledException;
import personal.GesundKlinik.shared.exception.DoctorAlreadyAssignedException;
import personal.GesundKlinik.shared.exception.PatientAlreadyAssignedException;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Column(name = "cancellation_reason")
    @Enumerated(EnumType.STRING)
    private CancellationReason cancellationReason;


    public void assignDoctor(Doctor doctor) {
        if (this.doctor == null || this.doctor.getId().equals(doctor.getId())) {
            this.doctor = doctor;
        } else
            throw new DoctorAlreadyAssignedException("Doctor has already been assigned.");
    }

    public void assignPatient(Patient patient) {
        if (this.patient == null || this.patient.getId().equals(patient.getId())) {
            this.patient = patient;
        } else {
            throw new PatientAlreadyAssignedException("Patient has already been assigned.");
        }
    }

    public void updateWith(Doctor doctor, LocalDateTime newDate) {
        if (doctor != null) {
            this.doctor = doctor;
        }
        if (newDate != null) {
            this.date = newDate;
        }
    }

    public void cancelWithReason(CancellationReason reason) {
        if (reason == null) {
            throw new IllegalArgumentException("Cancellation reason must not be null.");
        }
        if (this.cancellationReason != null) {
            throw new AppointmentAlreadyCancelledException("This appointment has already been cancelled.");
        }
        this.cancellationReason = reason;
    }
}

