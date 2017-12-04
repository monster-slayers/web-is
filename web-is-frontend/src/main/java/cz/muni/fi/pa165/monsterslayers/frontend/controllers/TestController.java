package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/test")
public class TestController {

    @RequestMapping(method = RequestMethod.POST)
    public final String create() {
        return "CREATE";
    }

    @RequestMapping(method = RequestMethod.GET)
    public final String find() {
        return "FIND";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public final String update() {
        return "GET";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public final String delete() {
        return "DELETE";
    }
}
