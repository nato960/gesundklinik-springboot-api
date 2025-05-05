package personal.GesundKlinik.modules.pacient.entity;

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
@Table(name = "pacients")
public class Pacient {

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

}

