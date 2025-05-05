package personal.GesundKlinik.shared.dto;


import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        @NotBlank
        String street,
        @NotBlank
        String number,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        String zipCode) {
}
