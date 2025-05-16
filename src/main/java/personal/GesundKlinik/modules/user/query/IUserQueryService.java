package personal.GesundKlinik.modules.user.query;


import org.springframework.security.core.userdetails.UserDetails;
import personal.GesundKlinik.modules.user.entity.User;


public interface IUserQueryService {

    User findById(final Long id);

    UserDetails findByLogin(final String username);

    boolean verifyLogin(final String login);

    User getReferenceById(final Long id);

}
