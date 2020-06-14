package com.example.crud.controller;

import com.example.crud.model.Document;
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

import java.time.LocalDate;

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
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private static final ObjectMapper om = new ObjectMapper();
    private static final Integer INVALID_ID = 666;

    private User george;
    private User joe;
    private Integer george_id;
    private Document notValidDocument;
    private Document validDocument;
//    private Document newValidDocument;
    private String newValidDocument;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        userRepository.deleteAll();

        george = new User();
        george.setFirstName("George");
        george.setLastName("Franklin");
        notValidDocument = new Document("notValidDocument", "001", LocalDate.now().minusDays(1), george);
        validDocument = new Document("validDocument", "002", LocalDate.now().plusYears(1), george);
        //newValidDocument = new Document("newValidDocument", "003", LocalDate.now().plusYears(2));
        newValidDocument ="{\"title\":\"newValidDocument\",\"number\":\"003\",\"expiryDate\":\"2022-06-14\"}";
        george.addDocument(notValidDocument);
        george.addDocument(validDocument);

        userRepository.save(george);
        george_id = george.getId();

        joe = new User();
        joe.setFirstName("Joe");
        joe.setLastName("Bloggs");

    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/users/{id}", george_id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(george.getLastName()))
                .andExpect(jsonPath("$.firstName").value(george.getFirstName()));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get("/users/{id}", INVALID_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetValidDocuments() throws Exception {
        mockMvc.perform(get("/users/{id}/documents", george_id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].expiryDate").value(validDocument.getExpiryDate().toString()));
    }

    @Test
    void tesCreateUserSuccess() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(om.writeValueAsString(joe)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    void tesCreateDocumentSuccess() throws Exception {
        mockMvc.perform(post("/users/{userId}/documents/new", george_id).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                //.content(om.writeValueAsString(newValidDocument)))
                .content(newValidDocument))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        mockMvc.perform(put("/users/{id}", george_id).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(om.writeValueAsString(joe)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value(joe.getLastName()))
                .andExpect(jsonPath("$.firstName").value(joe.getFirstName()));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/users/{id}", george_id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/users/{id}", INVALID_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
