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

    public void updateWith(Doctor updates) {
        if (updates.getName() != null)
            this.name = updates.getName();

        if (updates.getEmail() != null && !updates.getEmail().equals(this.email))
            this.email = updates.getEmail();

        if (updates.getPhone() != null && !updates.getPhone().equals(this.phone))
            this.phone = updates.getPhone();

        if (updates.getAddress() != null)
            this.address = updates.getAddress();
    }

}


