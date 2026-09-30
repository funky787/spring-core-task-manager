package com.example.taskmanager.config;

import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Bean
    public String applicationInfo(AppProperties properties) {
        return "Приложение: " + properties.getName();
    }

    @Bean
    @Profile("prod")
    public String priorityRepositoryInfo(
            @Qualifier("priorityTaskRepository") TaskRepository repository) {
        System.out.println("Через @Qualifier получен бин: "
                + repository.getClass().getSimpleName());
        return repository.getClass().getSimpleName();
    }
}
