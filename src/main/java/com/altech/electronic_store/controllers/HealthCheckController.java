package com.altech.electronic_store.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HealthCheck", description = "HealthCheck API")
@RestController("HealthCheckController")
@RequestMapping({"","/" ,"/healthcheck"})
public class HealthCheckController {
    @Operation(description = "Check healthy of application")
    @GetMapping()
    public ResponseEntity<String> healthcheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }
}
