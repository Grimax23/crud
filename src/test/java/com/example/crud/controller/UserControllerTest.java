package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final int TEST_USER_ID = 1;
    private final String USER_REQUEST = "{\"firstName\":\"Joe\", \"lastName\":\"Bloggs\"}";
    private User george;
    private User joe;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @BeforeEach
    void setup() {
        george = new User();
        george.setId(TEST_USER_ID);
        george.setFirstName("George");
        george.setLastName("Franklin");

        joe = new User();
        joe.setId(2);
        joe.setFirstName("Joe");
        joe.setLastName("Bloggs");

        given(repository.findById(TEST_USER_ID)).willReturn(java.util.Optional.ofNullable(george));
        given(repository.save(ArgumentMatchers.any())).willReturn(joe);
        given(repository.findAll()).willReturn(Collections.singletonList(george));
    }

    @Test
    void tesCreateSuccess() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(USER_REQUEST)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/users/{id}", TEST_USER_ID)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(george.getLastName()))
                .andExpect(jsonPath("$.firstName").value(george.getFirstName()));
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(george.getFirstName())));
    }

}
