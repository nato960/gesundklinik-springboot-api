package personal.GesundKlinik.modules.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.user.query.IUserQueryService;

@Service
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {


    private final IUserQueryService userQueryService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userQueryService.findByLogin(username);
    }
}