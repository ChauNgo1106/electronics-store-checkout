package com.altech.electronic_store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("TestController")
@RequestMapping("/test")
public class TestController {

    @GetMapping("/OK")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Ok");
    }
}
