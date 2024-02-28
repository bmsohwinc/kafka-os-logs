package com.bms.kos.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.kos.service.KOSService;
import com.bms.kos.service.KOSStreamsService;


@RestController
@RequestMapping("/kos")
public class KOSController {

    @Autowired
    private KOSService kosService;

    @Autowired
    private KOSStreamsService kosStreamsService;

    @GetMapping("/")
    public String hello() {
        return "It's KOS!";
    }

    @GetMapping("/start")
    public String start() throws IOException {
        return kosService.readSysLogs();
    }

    @GetMapping("/streams/start")
    public String startKStreams() {
        return kosStreamsService.startStreams();
    }
    
    @GetMapping("/streams/stop")
    public String stopKStreams() {
        return kosStreamsService.stopStreams();
    }
}
