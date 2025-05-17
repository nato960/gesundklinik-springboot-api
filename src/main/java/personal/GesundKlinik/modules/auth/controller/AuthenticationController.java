package personal.GesundKlinik.modules.auth.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import personal.GesundKlinik.modules.auth.dto.JwtResponse;
import personal.GesundKlinik.modules.auth.dto.LoginRequest;
import personal.GesundKlinik.modules.auth.service.IAuthService;


@RestController
@RequestMapping("/login")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse login(@RequestBody @Valid LoginRequest request){
        return authService.authenticate(request);
    }



}
