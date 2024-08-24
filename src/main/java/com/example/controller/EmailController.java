package com.example.controller;

import com.example.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/images/sendEmail")
    public String sendEmail(@RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("message") String message,
                            Model model) {
        try {
            emailService.saveEmail(username, email, message);
            model.addAttribute("message", "Сообщение email отправлено!");
        } catch (IOException e) {
            model.addAttribute("message", "Ошибка отправки сообщения email.");
            e.printStackTrace();
        }
        return "email-page";
    }

    @GetMapping("/images/email-page")
    public String showEmailPage(HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("/images/my-app")) {
            model.addAttribute("returnUrl", "/images/my-app");
        } else {
            model.addAttribute("returnUrl", "/images/images");
        }
        return "email-page";
    }


}

