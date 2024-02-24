package com.bms.kos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kos")
public class KOSController {

    @GetMapping("/")
    public String hello() {
        return "It's KOS!";
    }
}
