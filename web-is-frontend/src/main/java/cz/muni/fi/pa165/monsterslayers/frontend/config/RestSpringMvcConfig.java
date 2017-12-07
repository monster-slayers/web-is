package cz.muni.fi.pa165.monsterslayers.frontend.config;

import cz.muni.fi.pa165.monsterslayers.frontend.controllers.TestController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ImportResource({"classpath:/service-context.xml"})
@ComponentScan(basePackageClasses = {TestController.class})
public class RestSpringMvcConfig extends WebMvcConfigurerAdapter {
}
