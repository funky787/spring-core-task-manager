package com.example.taskmanager.runner;

import com.example.taskmanager.config.AppProperties;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AppRunner implements CommandLineRunner {

    private final TaskService taskService;
    private final AppProperties properties;
    private final ApplicationContext applicationContext;

    public AppRunner(
            TaskService taskService,
            AppProperties properties,
            ApplicationContext applicationContext) {
        this.taskService = taskService;
        this.properties = properties;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n  Шаг 1. Приветствие ");
        System.out.println("Добро пожаловать в " + properties.getName()
                + "! Лимит: " + properties.getMaxTasks()
                + ". Приоритет по умолчанию: " + properties.getDefaultPriority());

        System.out.println("\n Шаг 2. Добавление задач ");
        Task low = taskService.createTask("Купить продукты", "После работы", Task.Priority.LOW);
        Task medium = taskService.createTask("Сделать лабораторную", "Spring Core", Task.Priority.MEDIUM);
        Task high = taskService.createTask("Подготовиться к защите", "Повторить DI и IoC", Task.Priority.HIGH);
        taskService.getAllTasks().forEach(System.out::println);

        System.out.println("\n Шаг 3. Граничные случаи ");
        try {
            taskService.createTask("   ", "Ошибка", Task.Priority.LOW);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            while (true) {
                taskService.createTask("Дополнительная задача", null, null);
            }
        } catch (Exception e) {
            System.out.println("Ошибка лимита: " + e.getMessage());
        }

        System.out.println("\n Шаг 4 Изменение статусов");
        taskService.updateStatus(medium.getId(), Task.Status.IN_PROGRESS);
        taskService.updateStatus(high.getId(), Task.Status.DONE);

        System.out.println("DONE-задачи:");
        taskService.getDoneTasks().forEach(System.out::println);

        System.out.println("HIGH-задачи:");
        taskService.getHighPriorityTasks().forEach(System.out::println);

        System.out.println("\n Шаг 5. Prototype и ObjectProvider");
        System.out.println(taskService.getStats());
        System.out.println(taskService.getStats());

        System.out.println("\n Шаг 6 ApplicationContext ");
        TaskRepository repository = applicationContext.getBean(TaskRepository.class);
        System.out.println("Полученный бин: " + repository.getClass().getSimpleName());

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("Всего бинов: " + beanNames.length);
        System.out.println("Бины со словом task:");
        Arrays.stream(beanNames)
                .filter(name -> name.toLowerCase().contains("task"))
                .forEach(System.out::println);

        System.out.println("\n Текущий список задач ");
        taskService.getAllTasks().forEach(System.out::println);
    }
}
