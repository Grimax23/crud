package com.example.crud.controller;

import com.example.crud.model.Document;
import com.example.crud.model.User;
import com.example.crud.repository.DocumentRepository;
import com.example.crud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@RestController
@RequestMapping("/users/{userId}")
public class DocumentController {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    public DocumentController(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    @InitBinder("user")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/documents")
    public List<Document> getValidDocuments(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        List<Document> validDocuments = documentRepository.findAllByOwnerAndExpiryDateGreaterThan(user, LocalDate.now());
        if (validDocuments.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Valid documets Not Found");
        }
        return validDocuments;
    }

    @PostMapping("/documents/new")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Document> addDocument(@PathVariable int userId, @RequestBody Document document) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        if (document == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Document");
        } else if (document.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct ExpiryDate");
        } else {
            user.addDocument(document);
            documentRepository.save(document);
            return documentRepository.findAllByOwnerAndExpiryDateGreaterThan(user, LocalDate.now());
        }
    }


}
