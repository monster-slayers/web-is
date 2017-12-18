package cz.muni.fi.pa165.monsterslayers.frontend.security;

import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String email) {
        final UserDTO user = userFacade.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new MyUserPrincipal(user);
    }
}
