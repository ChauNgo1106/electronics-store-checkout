package com.altech.electronic_store.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("HealthCheckController")
@RequestMapping({"","/" ,"/healthcheck"})
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<String> healthcheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }
}
