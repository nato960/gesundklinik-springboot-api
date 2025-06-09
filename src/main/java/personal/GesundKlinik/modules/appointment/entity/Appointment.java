package personal.GesundKlinik.modules.appointment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.modules.patient.entity.Patient;
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
        if (this.doctor != null) {
            throw new DoctorAlreadyAssignedException("Doctor has already been assigned.");
        }
        this.doctor = doctor;
    }

    public void assignPatient(Patient patient) {
        if (this.patient != null) {
            throw new PatientAlreadyAssignedException("Patient has already been assigned.");
        }
        this.patient = patient;
    }

    public void updateWith(Doctor doctor, LocalDateTime newDate) {
        if (doctor != null) {
            this.doctor = doctor;
        }
        if (newDate != null) {
            this.date = newDate;
        }
    }
}

