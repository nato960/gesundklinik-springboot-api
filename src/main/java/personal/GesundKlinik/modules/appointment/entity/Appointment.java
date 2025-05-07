package personal.GesundKlinik.modules.appointment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.modules.pacient.entity.Pacient;

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
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Column(name = "cancellation_reason")
    @Enumerated(EnumType.STRING)
    private CancellationReason cancellationReason;

}

