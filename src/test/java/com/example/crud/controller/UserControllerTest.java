package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final int TEST_USER_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository users;

    private User george;

    @BeforeEach
    void setup() {
        george = new User();
        george.setId(TEST_USER_ID);
        george.setFirstName("George");
        george.setLastName("Franklin");
        given(this.users.findById(TEST_USER_ID)).willReturn(java.util.Optional.ofNullable(george));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(
                post("/users/new").param("firstName", "Joe").param("lastName", "Bloggs"))
                .andExpect(status().isOk()).andExpect(model().attributeHasErrors("user"));
    }
}