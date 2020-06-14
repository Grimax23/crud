package com.example.crud.controller;
/*
3. Сделать слой контроллера с заглушками и логировать вход в метод. Описать методы get, Post, put, delete.
4. Сделать curl-запросы, или что-то другое.
*/

import com.example.crud.model.User;
import com.example.crud.repository.DocumentRepository;
import com.example.crud.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct User");
        }
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", ex);
        }
    }

}
