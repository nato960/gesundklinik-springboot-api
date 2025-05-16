package personal.GesundKlinik.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRegisterResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("username")
        String login) {
}
