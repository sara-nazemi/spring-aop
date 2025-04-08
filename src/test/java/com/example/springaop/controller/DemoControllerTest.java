package com.example.springaop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.web.servlet.function.ServerResponse.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowAdminAccess() throws Exception {
        mockMvc.perform(get("/api/demo/user2"))
                .andExpect(status().isOk())
                .andExpect(content().string("User operation executed!"));

    }

    @Test
    void shouldDenyAccessForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/api/demo/error"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("access Denied!"));
    }
}
