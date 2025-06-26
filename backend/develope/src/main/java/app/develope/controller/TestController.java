package app.develope.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome to our page";
    }
}
