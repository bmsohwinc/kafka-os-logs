package com.bms.kos.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.kos.service.KOSService;

@RestController
@RequestMapping("/kos")
public class KOSController {

    @Autowired
    private KOSService kosService;

    @GetMapping("/")
    public String hello() {
        return "It's KOS!";
    }

    @GetMapping("/start")
    public String start() throws IOException {
        return kosService.readSysLogs();
    }
}
