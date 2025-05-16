package personal.GesundKlinik.modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @JsonProperty("username")
        String login,
        @NotBlank
        @JsonProperty("password")
        String password) {
}
