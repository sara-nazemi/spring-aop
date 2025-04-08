package com.example.springaop.services;

import com.example.springaop.service.DemoServiceImpl;
import com.example.springaop.userContext.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DemoServiceTest {

    @Autowired
    private DemoServiceImpl demoService;

    @BeforeEach
    void clearContext() {
        UserContext.clear();   //همیشه قبل از هر تست
    }

    @Test
    void shouldAllowAdminAccess() {
        UserContext.setRole("ADMIN");
        String result = demoService.secretOperation();
        assertEquals("This is a secret admin operation!", result);
    }

    @Test
    void shouldDenyAdminAccessForUserRole() {
        UserContext.setRole("USER");
        //Executable is functional interface then with lambda
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            demoService.secretOperation();
        });
        assertEquals("Access Denied!", exception.getMessage());

    }

    @Test
    void shouldAllowUserAccess() {
        UserContext.setRole("USER");
        String result = demoService.secretOperation();
        assertEquals("User Operation executed!", result);
    }

    @Test
    void shouldDenyUserAccessForGuestRole() {
        UserContext.setRole("GUEST");
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            demoService.secretOperation();
        });
        assertEquals("Access Denied!", exception.getMessage());
    }


}
