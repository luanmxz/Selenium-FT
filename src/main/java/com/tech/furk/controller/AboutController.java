package com.tech.furk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class AboutController {

    @Autowired
    private Environment env;

    @GetMapping()
    public String getVersion() {

        return env.getProperty("group") + "\n" + env.getProperty("artifact") + "\n" + env.getProperty("version");
    }
}