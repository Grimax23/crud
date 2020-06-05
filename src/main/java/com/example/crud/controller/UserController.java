package com.example.crud.controller;
/*
TODO
3. Сделать слой контроллера с заглушками и логировать вход в метод. Описать методы get, Post, put, delete.
4. Сделать curl-запросы, или что-то другое.
*/

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable int id) {
        return users.findById(id);
    }

    @GetMapping()
    public List<User> getAll() {
        return users.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return users.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        return users.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        users.deleteById(id);
    }

}
