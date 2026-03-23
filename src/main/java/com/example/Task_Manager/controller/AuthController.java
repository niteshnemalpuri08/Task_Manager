package com.example.Task_Manager.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Task_Manager.model.User;
import com.example.Task_Manager.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/test")
    public String test() {
        return "Auth controller working";
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        Optional<User> existingUser = repo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            response.put("message", "Username already exists");
            return response;
        }

        user.setRole("USER"); // ✅ ADD THIS LINE (VERY IMPORTANT)

        repo.save(user);
        response.put("message", "User registered successfully");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        Optional<User> existingUser = repo.findByUsername(user.getUsername());

        if (existingUser.isPresent() &&
            existingUser.get().getPassword().equals(user.getPassword())) {
            response.put("message", "Login successful");
        } else {
            response.put("message", "Invalid username or password");
        }

        return response;
    }
}