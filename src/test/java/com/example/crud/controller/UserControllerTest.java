package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerIntegrationTest {

    private static final int TEST_USER_ID = 1;
    private static final ObjectMapper om = new ObjectMapper();
    private User george;
    private User joe;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    @Transactional
    void setup() {
        repository.deleteAll();

        george = new User();
        george.setId(TEST_USER_ID);
        george.setFirstName("George");
        george.setLastName("Franklin");
        repository.save(george);

        joe = new User();
        joe.setId(2);
        joe.setFirstName("Joe");
        joe.setLastName("Bloggs");
    }

    @Test
    @Transactional
    void testGet() throws Exception {
        mockMvc.perform(get("/users/{id}", TEST_USER_ID)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(george.getLastName()))
                .andExpect(jsonPath("$.firstName").value(george.getFirstName()));
    }

    @Test
    @Transactional
    void testGetAll() throws Exception {
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value(george.getFirstName()))
                .andExpect(jsonPath("$[0].firstName").value(george.getFirstName()));
    }

    @Test
    @Transactional
    void tesCreateSuccess() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(om.writeValueAsString(joe))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    @Transactional
    void testUpdate() throws Exception {
        mockMvc.perform(put("/users/{id}", TEST_USER_ID).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(om.writeValueAsString(joe))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    @Transactional
    void testDelete() throws Exception {
        mockMvc.perform(delete("/users/{id}", TEST_USER_ID))
                .andExpect(status().isNoContent());
    }
}
