package com.example.crud.controller;
/*
TODO
3. Сделать слой контроллера с заглушками и логировать вход в метод. Описать методы get, Post, put, delete.
4. Сделать curl-запросы, или что-то другое.
*/

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @PostMapping("/users/new")
    public User create(@RequestBody User user) {
        log.info("create");
        return this.users.save(user);
    }

    @GetMapping("/users/{id}")
    public Optional<User> get(@PathVariable int id) {
        log.info("get {}", id);
        return this.users.findById(id);
    }

    @PutMapping("/users/{id}/edit")
    public String update(@RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        user.setId(id);
        this.users.save(user);
        return "redirect:/users/{id}";
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        this.users.deleteById(id);
    }

    @GetMapping("/users")
    public List<User> getAll() {
        log.info("getAll");
        return this.users.findAll();
    }
}
