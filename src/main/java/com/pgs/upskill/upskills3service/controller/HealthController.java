package com.pgs.upskill.upskills3service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping
    @RequestMapping("/")
    public void getWriteUrl() {

    }
}
