package com.example.Task_Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Task_Manager.TokenUtil;
import com.example.Task_Manager.model.Task;
import com.example.Task_Manager.model.User;
import com.example.Task_Manager.repository.TaskRepository;
import com.example.Task_Manager.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController { 

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TokenUtil tokenUtil;

    // ✅ CREATE TASK
    @PostMapping
    public Object createTask(@RequestBody Task t, @RequestHeader("Authorization") String token) {

        String username = tokenUtil.validateToken(token);

        if (username == null) {
            return "Unauthorized";
        }

        t.setUsername(username);
        return taskRepo.save(t);
    }

    // ✅ GET USER TASKS
    @GetMapping
    public Object getTask(@RequestHeader("Authorization") String token) {

        String username = tokenUtil.validateToken(token);

        if (username == null) {
            return "Unauthorized";
        }

        return taskRepo.findByUsername(username);
    }

    // ✅ ADMIN API
    @GetMapping("/admin")
    public Object getAllTasks(@RequestHeader("Authorization") String token) {

        String username = tokenUtil.validateToken(token);

        if (username == null) {
            return "Unauthorized";
        }

        Optional<User> userOpt = userRepo.findByUsername(username);

        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();

        if (!user.getRole().equals("ADMIN")) {
            return "Access Denied";
        }

        return taskRepo.findAll();
    }
}