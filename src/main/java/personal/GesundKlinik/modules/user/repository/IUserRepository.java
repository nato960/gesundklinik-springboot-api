package personal.GesundKlinik.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import personal.GesundKlinik.modules.user.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);

    boolean existsBylogin(String login);
}
