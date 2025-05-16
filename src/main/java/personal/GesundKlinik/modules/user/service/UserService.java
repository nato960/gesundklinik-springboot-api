package personal.GesundKlinik.modules.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.GesundKlinik.modules.user.entity.User;
import personal.GesundKlinik.modules.user.query.IUserQueryService;
import personal.GesundKlinik.modules.user.repository.IUserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final IUserRepository repository;
    private final IUserQueryService queryService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User entity){

       queryService.verifyLogin(entity.getLogin());

       entity.setPassword(passwordEncoder.encode(entity.getPassword()));

       return repository.save(entity);
    }

}
