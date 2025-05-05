package personal.GesundKlinik.shared.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personal.GesundKlinik.shared.dto.AddressDto;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Address {

    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;


    public Address(AddressDto dto) {
        this.street = dto.street();
        this.number = dto.number();
        this.city = dto.city();
        this.state = dto.state();
        this.zipCode = dto.zipCode();
    }


    public void update(AddressDto dto) {
        if (dto.street() != null) {
            this.street = dto.street();
        }
        if (dto.number() != null) {
            this.number = dto.number();
        }
        if (dto.city() != null) {
            this.city = dto.city();
        }
        if (dto.state() != null) {
            this.state = dto.state();
        }
        if (dto.zipCode() != null) {
            this.zipCode = dto.zipCode();
        }
    }
}
