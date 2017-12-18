package cz.muni.fi.pa165.monsterslayers.frontend.security;

import org.springframework.context.annotation.Import;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Initializer for security stuff
 *
 * @author Ondrej Budai
 */
@Import(SecurityConfiguration.class)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
