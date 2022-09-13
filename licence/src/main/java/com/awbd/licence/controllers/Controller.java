package com.awbd.licence.controllers;

import com.awbd.licence.config.PropertiesConfig;
import com.awbd.licence.model.Licence;
import com.awbd.licence.service.LicenceService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private LicenceService licenceService;

    @GetMapping("/licence/{type}")
    public Licence getLicence(@PathVariable String type) {
        return licenceService.findByType(type);
    }
}