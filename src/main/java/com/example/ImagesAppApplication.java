package com.example;

import com.example.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ImagesAppApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ImagesAppApplication.class, args);

        // Создание пользователей и ролей
        UserService userService = ctx.getBean(UserService.class);
        userService.createUsersAndRoles();

    }
}
