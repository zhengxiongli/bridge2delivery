package com.thoughtworks.bridge2delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/swagger")
    public String swagger() {
        return "swagger";
    }
}
