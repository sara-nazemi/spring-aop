package com.example.springaop.controller;

import com.example.springaop.service.DemoServiceImpl;
import com.example.springaop.userContext.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    DemoServiceImpl demoService;

    @GetMapping("/api/demo")
    public String hello() {
        return demoService.sayHello();
    }

    @GetMapping("/admin")
    public String admin(@RequestHeader("Role") String role) {
        UserContext.setRole(role);
        try {
            return demoService.secretOperation();
        } finally {
            UserContext.clear();
        }

    }


    @GetMapping("/user")
    public String user(@RequestHeader("Role") String role) {
        UserContext.setRole(role);
        try {
            return demoService.userSecretOperation();
        } finally {
            UserContext.clear();
        }
    }

    @GetMapping("/user2")
    public ResponseEntity<String> getUserStuff() {
        String result = demoService.userSecretOperation();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<String> getError() {
        throw new SecurityException("Access Denied!");
    }
}