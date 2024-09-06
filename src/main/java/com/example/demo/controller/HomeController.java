package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/meomeo")
    public String hamnaytenlameomeo() {
        
        return "meomeo"; // Trả về file meomeo.html
    }
}
