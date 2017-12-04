package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@ExposesResourceFor(CategoryDTO.class)
@RequestMapping("/")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public void categories() {
        LoggerFactory.getLogger(TestController.class).debug("We were called!");
    }
}
