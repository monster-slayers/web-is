package cz.muni.fi.pa165.monsterslayers.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * Service configuration class 
 * 
 * @author Tomáš Richter
 */
@Configuration
@ComponentScan({"cz.muni.fi.pa165.service", "cz.muni.fi.pa165.service.facadeImpl"})
public class ServiceConfig {
}
