package cz.muni.fi.pa165.monsterslayers.service;

import org.springframework.stereotype.Service;

/**
 * Service for creation and comparsion of securely hashed passwords.
 */
@Service
public interface PasswordService {

    /**
     * Creates hash from plain text password.
     * @param plainTextPassword Password in plain text format.
     * @return Hashed password.
     */
    String createHash(String plainTextPassword);

    /**
     * Checks whether the given password equals to the stored hash.
     * @param password Given password (possibly by user trying to log in).
     * @param hash Hash of password (possibly stored in DB).
     * @return True if the password matches the hash, false otherwise.
     */
    boolean checkHash(String password, String hash);
}
