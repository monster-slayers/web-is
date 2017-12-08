package cz.muni.fi.pa165.monsterslayers.frontend.config;

import cz.muni.fi.pa165.monsterslayers.frontend.controllers.TestController;
import cz.muni.fi.pa165.monsterslayers.sample_data.SampleDataConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

@EnableWebMvc
@Configuration
@Import(SampleDataConfig.class)
@ComponentScan(basePackageClasses = {TestController.class})
public class RestSpringMvcConfig extends WebMvcConfigurerAdapter {
}
