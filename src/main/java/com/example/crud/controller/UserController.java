package com.example.crud.controller;
/*
TODO
3. Сделать слой контроллера с заглушками и логировать вход в метод. Описать методы get, Post, put, delete.
4. Сделать curl-запросы, или что-то другое.
*/

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public User create(@RequestBody User user) {
        final User savedUser = repository.save(user);
        return savedUser;
    }

    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}/edit")
    public String update(@RequestBody User user, @PathVariable int id) {
        user.setId(id);
        repository.save(user);
        return "redirect:/users/{id}";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repository.deleteById(id);
    }

    @GetMapping()
    public List<User> getAll() {
        return repository.findAll();
    }
}
