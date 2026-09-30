package com.example.taskmanager.service;

import com.example.taskmanager.config.AppProperties;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final AppProperties properties;
    private final ObjectProvider<TaskStatsService> statsProvider;
    private final AtomicLong idGenerator = new AtomicLong(0);

    public TaskServiceImpl(
            TaskRepository repository,
            AppProperties properties,
            ObjectProvider<TaskStatsService> statsProvider) {
        this.repository = repository;
        this.properties = properties;
        this.statsProvider = statsProvider;
    }

    @PostConstruct
    public void init() {
        System.out.println("TaskService инициализирован");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Завершение работы. Задач в хранилище: "
                + repository.findAll().size());
    }

    @Override
    public Task createTask(String title, String description, Task.Priority priority) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }

        if (repository.findAll().size() >= properties.getMaxTasks()) {
            throw new IllegalStateException(
                    "Достигнут лимит задач: " + properties.getMaxTasks());
        }

        Task.Priority finalPriority =
                priority != null ? priority : properties.getDefaultPriority();

        Task task = new Task(
                idGenerator.incrementAndGet(),
                title,
                description,
                finalPriority,
                Task.Status.NEW
        );

        return repository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> getDoneTasks() {
        return repository.findAll().stream()
                .filter(task -> task.getStatus() == Task.Status.DONE)
                .toList();
    }

    @Override
    public List<Task> getHighPriorityTasks() {
        return repository.findAll().stream()
                .filter(task -> task.getPriority() == Task.Priority.HIGH)
                .toList();
    }

    @Override
    public void updateStatus(Long id, Task.Status status) {
        repository.updateStatus(id, status);
    }

    @Override
    public void deleteTask(Long id) {
        repository.delete(id);
    }

    @Override
    public String getStats() {
        TaskStatsService stats = statsProvider.getObject();
        return "TaskStatsService UUID: " + stats.getInstanceId()
                + ", задач: " + repository.findAll().size();
    }
}
