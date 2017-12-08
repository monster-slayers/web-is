package cz.muni.fi.pa165.monsterslayers.sample_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

@Configuration
@ImportResource({"classpath:/service-context.xml"})
@ComponentScan(basePackageClasses = SampleDataLoadingFacadeImpl.class)
public class SampleDataConfig {

    @Autowired
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading(){
        sampleDataLoadingFacade.loadData();
    }
}
