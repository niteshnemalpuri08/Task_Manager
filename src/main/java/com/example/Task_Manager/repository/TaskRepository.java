package com.example.Task_Manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Task_Manager.model.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUsername(String username); // ✅ REQUIRED
}