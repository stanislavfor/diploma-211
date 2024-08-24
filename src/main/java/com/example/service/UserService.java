package com.example.service;

import com.example.model.User;
import com.example.model.Role;
import com.example.model.UserRole;
import com.example.repository.UserRepository;
import com.example.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service
@EnableAspectJAutoProxy
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void createUsersAndRoles() {

        // Создание пользователей
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin
        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user
        User anonymous = new User();
        anonymous.setLogin("anon");
        anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon

        // Сохранение пользователей в репозитории
        admin = userRepository.save(admin);
        user = userRepository.save(user);
        anonymous = userRepository.save(anonymous);

        // Создание ролей
        createRole(admin.getId(), Role.ADMIN.getName());
        createRole(admin.getId(), Role.USER.getName());
        createRole(user.getId(), Role.USER.getName());
    }

    // Метод для создания роли
    private void createRole(Long userId, String roleName) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleName(roleName);
        userRoleRepository.save(userRole);
    }


}
