package cz.muni.fi.pa165.monsterslayers.frontend.security;

import cz.muni.fi.pa165.monsterslayers.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Wrapper for password service.
 *
 * @author Ondrej Budai
 */
@Service
public class MyPasswordEncoder implements PasswordEncoder {
    @Autowired
    private PasswordService passwordService;

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordService.createHash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String hash) {
        return passwordService.checkHash(rawPassword.toString(), hash);
    }
}
