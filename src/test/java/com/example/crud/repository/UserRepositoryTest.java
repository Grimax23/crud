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
    protected UserRepository userRepository;
/*
    @Autowired
    protected DocumentRepository documentRepository;
*/

    private User george;
    private User joe;
    private Integer george_id;
    private Integer joe_id;

/*
    public static final List<Document> DOCUMENTS = Arrays.asList(
            new Document("Недействительный документ", "123", LocalDate.of(2020, 2, 27)),
            new Document("Старый документ", "456", LocalDate.of(2019, 2, 28)),
            new Document("Действующий документ", "789", LocalDate.of(2021, 4, 1)),
            new Document("Действующий документ", "321", LocalDate.of(2020, 3, 5))
    );
*/

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
//        documentRepository.deleteAll();


        george = new User();
        george.setFirstName("George");
        george.setLastName("Franklin");
//        george.addDocument();Documents(new HashSet(doc1));
        userRepository.save(george);
        george_id = george.getId();

        //documentRepository.save(doc1);


        joe = new User();
        joe.setFirstName("Joe");
        joe.setLastName("Bloggs");
    }

    @Test
    void getByID() {
        Optional<User> byId = userRepository.findById(george_id);
        assertEquals(byId, Optional.ofNullable(george));
    }

    @Test
    void getAll() {
        assertEquals(userRepository.findAll().size(), 1);
    }

    @Test
    void save() {
        userRepository.save(joe);
        joe_id = joe.getId();
        assertEquals(userRepository.findAll().size(), 2);
        assertEquals(userRepository.findById(george_id), Optional.ofNullable(george));
        assertEquals(userRepository.findById(joe_id), Optional.ofNullable(joe));
    }

    @Test
    void deleteById() {
        userRepository.deleteById(george_id);
        assertEquals(userRepository.findAll().size(), 0);
    }
}