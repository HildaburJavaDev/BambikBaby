package com.hildabur.bambikbaby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/her")
    @ResponseBody
    public String index() {
        System.out.println("HERE!");
        return "Greetings from Spring Boot!";
    }
}
