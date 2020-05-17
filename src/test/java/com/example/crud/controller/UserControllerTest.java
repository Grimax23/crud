package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository users;

    @BeforeEach
    void setup() {
        george = new User();
        george.setId(TEST_USER_ID);
        george.setFirstName("George");
        george.setLastName("Franklin");
        given(this.users.findById(TEST_USER_ID)).willReturn(java.util.Optional.ofNullable(george));
    }

    @Test
    void tesCreateSuccess() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(USER_REQUEST)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value("Bloggs"))
                .andExpect(jsonPath("$.firstName").value("Joe"))
                //.andExpect(model().attributeExists("user"))
                //.andExpect(model().attribute("user", hasProperty("lastName", is("Bloggs"))))
                //.andExpect(model().attribute("user", hasProperty("firstName", is("Joe"))))
        ;
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/users/{id}", TEST_USER_ID)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value("Franklin"))
                .andExpect(jsonPath("$.firstName").value("George"))
                //.andExpect(model().attribute("user", hasProperty("lastName", is("Franklin"))))
                //.andExpect(model().attribute("user", hasProperty("firstName", is("George"))))
        ;
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                //.andExpect(jsonPath("$.lastName").value("Franklin"))
                //.andExpect(jsonPath("$.firstName").value("George"))
                //.andExpect(model().attribute("user", hasProperty("lastName", is("Franklin"))))
                //.andExpect(model().attribute("user", hasProperty("firstName", is("George"))))
        ;
    }



}
