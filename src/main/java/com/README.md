# SQL-запросы для создания базы данных PostgreSQL

Создание базы данных и таблиц в PostgreSQL:

```sql
-- Создать базу данных
CREATE
DATABASE images_db;



-- Создать таблицу users
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Создать таблицу item
CREATE TABLE item
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    link        VARCHAR(200)
);

```

Добавление значений в Таблицу 'users'

```sql
INSERT INTO users (login, password)
VALUES ('admin', '$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG'), -- пароль зашифрован
       ('user', '$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq') -- пароль зашифрован


```

## Страница авторизации (my-app.html):

### URL: http://localhost:8081/images/my-app

Это страница, на которой пользователи могут ввести свои логин и пароль для входа в систему.

## Главная страница (index.html):

### URL: http://localhost:8081/images/in

<br><br>