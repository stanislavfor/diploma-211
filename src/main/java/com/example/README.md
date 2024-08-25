## Структура проекта:

В директории src/main/resources/templates расположены три файла: index.html, my-app.html и email-page.html.<br>
В директории controller есть следующие контроллеры:<br>

- ImageController для index.html
- MyAppController для my-app.html
- EmailController для email-page.html
-

В директории model есть классы Item.java и User.java.<br>
В директории service есть следующие сервисы:<br>

- EmailService.java для email-page.html
-
- FileService.java для index.html
- ImageService.java для index.html
  В директории repository есть интерфейсы ItemRepository.java и UserRepository.java.<br>
-

Авторизация и безопасность:<br>
Приложение использует Spring Security для обеспечения безопасности.<br>
В базе данных images_db есть следующие сущности:<br>

- item (id, name, description, link).
- users (id, login, password).
- roles (id, name).
-

Таблица user_roles связывает пользователей с ролями (many-to-many).
В конкретном случае, user_roles(1,1) означает, что пользователь с id=1 имеет роль с id=1 (admin).<br>
На странице my-app.html есть форма авторизации:<br>

После успешной авторизации пользователь перенаправляется на страницу index.html,
где есть кнопка “Logout” для выхода из системы и возврата на страницу my-app.html.

Исправление кода:
Для того чтобы первичная страница проекта была http://localhost:8081/images/my-app, <br>
а не http://localhost:8081/images, вам нужно настроить маршруты в Spring Boot.<br>
Если переход на страницу происходит без авторизации, <br>
то должна открываться страница oops.html с предупреждением о невозможности перехода.<br>
Для проверки введенных данных в форму авторизации используйте классы <br>
LoginService.java, LoginController.java и SecurityConfig.java.<br>
Для работы с зависимостями Maven добавьте необходимые зависимости в pom.xml.<br>
Настройка проекта необходима в файле application.yml.<br>

# Настройки для файла application.yml

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/images_db
    username: postgres # указать имя User из базы данных
    password: password # указать пароль, записанный для данной базы данных
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

```

# SQL-запросы для создания базы данных PostgreSQL

Создание базы данных и таблиц в PostgreSQL:

```sql
-- Создать базу данных
CREATE
DATABASE images_db;

-- Переключиться на созданную базу данных
\c
images_db;

-- Создать таблицу users
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Создать таблицу item
CREATE TABLE IF NOT EXISTS item
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

Для создания таблицы Role в PostgreSQL, можно использовать SQL-запрос:

```sql
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

```

Таблица, связывающая пользователей с ролями (many-to-many):

```sql
CREATE TABLE user_roles
(
    user_id INT REFERENCES users (id),
    role_id INT REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

```

Добавление данных в таблицу roles (администратор, пользователь и т. д.):

```sql
INSERT INTO roles (name)
VALUES ('admin'),
       ('user');

```

Присвоение ролей пользователям:

```sql
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1), -- пользователь "admin" имеет роль "admin"
       (2, 2); -- пользователь "user" имеет роль "user"

```

## Thymeleaf шаблон страницы my-login.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Login</title>
</head>
<body>
<form th:action="@{/login}" method="post">
    <div>
        <label for="login">Login:</label>
        <input type="text" id="login" name="username"/>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/>
    </div>
    <div>
        <button type="submit">Login</button>
    </div>
</form>
</body>
</html>

```

## Хеширование пароля

```
String rawPassword = "admin";
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
boolean matches = encoder.matches(rawPassword, "$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG");
System.out.println(matches); 
// Должно быть true

```

## Страница авторизации (my-app.html):

### URL: http://localhost:8081/images/my-app

Это страница, на которой пользователи могут ввести свои логин и пароль для входа в систему.

## Главная страница (index.html):

### URL: http://localhost:8081/images/in

<br><br>