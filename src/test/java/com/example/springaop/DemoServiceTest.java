package com.example.springaop;

import com.example.springaop.service.DemoService;
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
    private DemoService demoService;

    @BeforeEach
    void clearContext() {
        UserContext.clear();   //همیشه قبل از هر تست
    }

    @Test
    void shouldAllowAdminAccess() {
        UserContext.setRole("ADMIN");
        String result = demoService.secretOperation();
        assertEquals("Admin operation executed!", result);
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

    void shouldAllowUserAccess() {
        UserContext.setRole("USER");
        String result = demoService.secretOperation();
        assertEquals("User Operation executed!", result);
    }

    void shouldDenyUserAccessForGuestRole() {
        UserContext.setRole("GUEST");
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            demoService.secretOperation();
        });
        assertEquals("Access Denied!", exception.getMessage());
    }


}
