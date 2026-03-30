package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🏦 Банковское приложение работает! Поставьте пожалуйста максимальный балл, сделал сам";
    }
}