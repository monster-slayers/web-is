package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public final String create() {
        return "CREATE";
    }

    @GetMapping
    public final Map<String, String> find() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        return map;
    }

    @PutMapping
    public final String update() {
        return "GET";
    }

    @DeleteMapping
    public final String delete() {
        return "DELETE";
    }
}
