# Spring Core Task Manager

Проект Task Manager на Spring Boot.

Программа работает в консоли. В ней можно создавать задачи, менять их статус и смотреть список задач.

Главная цель работы разобраться с основными вещами Spring Core:
- Бины;
- Dependency Injection;
- `@Component`;
- `@Bean`;
- `@Primary`;
- `@Qualifier`;
- `@Profile`;
- `@Value`;
- `@PostConstruct`;
- `@PreDestroy`;
- Singleton и Prototype;
- `ObjectProvider`;
- `ApplicationContext`.

---

## Задача

В программе есть класс `Task`.

У задачи есть:

- id;
- название;
- описание;
- приоритет;
- статус.

Приоритет может быть:


LOW
MEDIUM
HIGH


Статус:


NEW
IN_PROGRESS
DONE


---

## Структура проекта

Основные папки проекта:


model
repository
service
config
runner


`model` — находится класс задачи.

`repository` — работа со списком задач.

`service` — основная логика программы.

`config` — настройки Spring.

`runner` — код который запускается при старте программы.

---



## Что было сделано

В этой работе я сделал простой Task Manager и использовал основные возможности Spring Core:


@Component
@Service
@Configuration
@Bean
@Primary
@Qualifier
@Profile
@Value
@PostConstruct
@PreDestroy
@Scope
ObjectProvider
ApplicationContext
CommandLineRunner


Также сделал два профиля `dev` и `prod`.

В `dev` используется обычный репозиторий, а в `prod` задачи сортируются:


HIGH → MEDIUM → LOW


Программа запускается и все пункты задания работают.
