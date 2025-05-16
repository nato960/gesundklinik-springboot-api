package personal.GesundKlinik.modules.auth.service;

import personal.GesundKlinik.modules.auth.dto.JwtResponse;
import personal.GesundKlinik.modules.auth.dto.LoginRequest;

public interface IAuthService {

    JwtResponse authenticate(LoginRequest request);

}
