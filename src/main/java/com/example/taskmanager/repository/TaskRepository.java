package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task save(Task task);
    void delete(Long id);
    void updateStatus(Long id, Task.Status status);
}
