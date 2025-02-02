package com.website.jobfinder.controller;

import com.website.jobfinder.exception.IdInvalidException;
import com.website.jobfinder.model.entity.User;
import com.website.jobfinder.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/{id}")
    public User fetchUserById(@PathVariable Long id) {
        return this.userService.fetchUserById(id);
    }

    @GetMapping("/users")
    public List<User> fetchAllUsers() {
        return this.userService.fetchAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return this.userService.handleUpdateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("ID Khong Hop Le");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }
}
