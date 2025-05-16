package personal.GesundKlinik.modules.auth.jwt;

import personal.GesundKlinik.modules.user.entity.User;

public interface IJwtService {

    String generateToken(User user);

    String getSubject(String token);

}
