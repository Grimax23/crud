package com.example.crud.controller;
/*
TODO
3. Сделать слой контроллера с заглушками и логировать вход в метод. Описать методы get, Post, put, delete.
4. Сделать curl-запросы, или что-то другое.
*/

import com.example.crud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    //curl -X GET http://localhost:8080/users/1
    @GetMapping("/users/{id}")
    public User get(@PathVariable int id) {
        log.info("get {}", id);
        //TODO return service.get(userId);
        return null;
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        //TODO service.delete(id);
    }

    @PutMapping(value = "/users/{id}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        //TODO service.update(user, userId);
    }

    @PostMapping("/users/new")
    public User create() {
        log.info("create");
        //TODO service.update(user, userId);
        return null;
    }

    @GetMapping("/users")
    public User getAll() {
        log.info("getAll");
        //TODO return service.getAll();
        return null;
    }
}
