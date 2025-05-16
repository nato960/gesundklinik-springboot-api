package personal.GesundKlinik.modules.user.query;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.user.entity.User;
import personal.GesundKlinik.modules.user.repository.IUserRepository;
import personal.GesundKlinik.shared.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class UserQueryService implements IUserQueryService{

    private final IUserRepository repository;

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("User with ID " + id + " not found"));
    }

    @Override
    public User getReferenceById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("User with ID " + id + " not found");
        }
        return repository.getReferenceById(id);
    }

    public UserDetails findByLogin(String username){
        var user = repository.findByLogin(username);
        if (user == null){
            throw new UsernameNotFoundException("User with login '" + username + "' not found");
        }
        return user;
    }

    @Override
    public boolean verifyLogin(String login) {
        return repository.existsBylogin(login);
    }


}
