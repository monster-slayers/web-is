package cz.muni.fi.pa165.monsterslayers.entities.enums;

/**
 * Enumerates levels of rights that a User can have. These are used to validate access to certain parts of the system.
 * A User with higher access rights (CLIENT = 0, MANAGER = 2) is supposed to have all the right the ones below have.
 *
 * @author David Kizivat
 */
public enum RightsLevel {
    CLIENT,
    HERO,
    MANAGER
}
