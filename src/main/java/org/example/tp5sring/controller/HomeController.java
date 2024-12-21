package org.example.tp5sring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";  // Redirige vers hello.html
    }
}
