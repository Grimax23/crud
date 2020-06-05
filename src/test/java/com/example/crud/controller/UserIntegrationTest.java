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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository repository;

    private MockMvc mockMvc;

    private static final ObjectMapper om = new ObjectMapper();
    private User george;
    private User joe;
    private Integer george_id;
    private Integer joe_id;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        repository.deleteAll();

        george = new User();
        george.setFirstName("George");
        george.setLastName("Franklin");
        repository.save(george);
        george_id = george.getId();


        joe = new User();
        joe.setFirstName("Joe");
        joe.setLastName("Bloggs");
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/users/{id}", george_id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(george.getLastName()))
                .andExpect(jsonPath("$.firstName").value(george.getFirstName()));
    }

/*    @Test
    public void testUserIsNotFound() throws Exception {
        mockMvc.perform(get("/users/{id}", 666))
                .andExpect(status().isNotFound());
    }*/

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastName").value(george.getLastName()))
                .andExpect(jsonPath("$[0].firstName").value(george.getFirstName()));

    }

    @Test
    void tesCreateSuccess() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(om.writeValueAsString(joe))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(put("/users/{id}", george_id).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(om.writeValueAsString(joe))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/users/{id}", george_id)).andDo(print())
                .andExpect(status().isNoContent());
    }

/*
    @Test
    public void testUserToDeleteIsNotFound() throws Exception {
        mockMvc.perform(delete("/users/{id}", 666)).andDo(print())
                .andExpect(status().isNotFound());
    }
*/
}
