package com.hse.flowerapp.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestRestController {
    @GetMapping("/test")
    public String testServer() {
        return "Server OK ";
    }
}
