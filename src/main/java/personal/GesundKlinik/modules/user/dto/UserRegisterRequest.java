package personal.GesundKlinik.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @NotBlank
        @JsonProperty("username")
        String login,
        @NotBlank
        @JsonProperty("password")
        String password) {
}
