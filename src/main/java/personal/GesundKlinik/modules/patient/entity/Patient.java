package personal.GesundKlinik.modules.patient.entity;

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
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;

    private LocalDate birthDate;

    private String phone;

    @Embedded
    private Address address;

    private Boolean active;


    public void deactivate() {
        this.active = false;
    }

    public void updateWith(Patient updates) {
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

