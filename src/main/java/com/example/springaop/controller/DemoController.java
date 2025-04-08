package com.example.springaop.controller;

import com.example.springaop.service.DemoService;
import com.example.springaop.userContext.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    DemoService demoService;

    @GetMapping("/hello")
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
            return demoService.secretOperation();
        } finally {
            UserContext.clear();
        }
    }
}