package com.example.config;

import com.example.secondary.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(requests -> requests
                                .requestMatchers("/images/my-app", "/images/email-page", "/images/sendEmail")
                                .permitAll()
                                .requestMatchers("/images/in", "/images/in/add", "/images/in/edit/**", "/images/in/delete/**", "edit-item", "index")
                                .hasAuthority(Role.ADMIN.getName())
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/images/my-app")
                        .defaultSuccessUrl("/images/in", true)
                        .failureUrl("/images/my-app?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/images/my-app")
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/oops")
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

