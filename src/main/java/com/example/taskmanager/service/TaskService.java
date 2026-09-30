package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, Task.Priority priority);
    List<Task> getAllTasks();
    List<Task> getDoneTasks();
    List<Task> getHighPriorityTasks();
    void updateStatus(Long id, Task.Status status);
    void deleteTask(Long id);
    String getStats();
}
