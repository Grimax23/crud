package com.example.crud.repository;

import com.example.crud.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    protected UserRepository repository;

    private User george;
    private User joe;
    private Integer george_id;
    private Integer joe_id;

    @BeforeEach
    void setUp() {
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
    void getByID() {
        Optional<User> byId = repository.findById(george_id);
        assertEquals(byId, Optional.ofNullable(george));
    }

    @Test
    void getAll() {
        assertEquals(repository.findAll().size(), 1);
    }

    @Test
    void save() {
        repository.save(joe);
        joe_id = joe.getId();
        assertEquals(repository.findAll().size(), 2);
        assertEquals(repository.findById(george_id), Optional.ofNullable(george));
        assertEquals(repository.findById(joe_id), Optional.ofNullable(joe));
    }

    @Test
    void deleteById() {
        repository.deleteById(george_id);
        assertEquals(repository.findAll().size(), 0);
    }
}