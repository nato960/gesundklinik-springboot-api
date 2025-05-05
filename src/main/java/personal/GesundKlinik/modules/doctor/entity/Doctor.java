package personal.GesundKlinik.modules.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import personal.GesundKlinik.shared.model.Address;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(unique = true)
    private String crm;

    private LocalDate birthDate;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Embedded
    private Address address;

    private Boolean active;


    public void deactivate() {
        this.active = false;
    }


}


