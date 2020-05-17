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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @PostMapping()
    public User create(@RequestBody User user, BindingResult result) {
        final User savedUser = this.users.save(user);
        return savedUser;
    }

    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable int id) {
        return this.users.findById(id);
    }

    @PutMapping("/{id}/edit")
    public String update(@RequestBody User user, @PathVariable int id) {
        user.setId(id);
        this.users.save(user);
        return "redirect:/users/{id}";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.users.deleteById(id);
    }

    @GetMapping()
    public List<User> getAll() {
        return this.users.findAll();
    }
}
