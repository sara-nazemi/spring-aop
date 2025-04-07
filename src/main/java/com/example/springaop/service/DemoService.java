package com.example.springaop.service;

import com.example.springaop.annotations.SecureAccess;
import com.example.springaop.annotations.TrackTime;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @TrackTime
    public String sayHello() {
        return "Hello from service!";
    }


    //    @RequiresRole("ADMIN")
    @SecureAccess(role = "ADMIN", description = "Secret admin function")
    public String secretOperation() {
        return "This is a secret admin operation!";
    }
}
