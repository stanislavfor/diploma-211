package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OopsController {

    @GetMapping("/oops")
    public String loginPage() {

        return "oops";
    }



}
