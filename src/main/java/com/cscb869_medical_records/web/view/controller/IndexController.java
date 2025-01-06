package com.cscb869_medical_records.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String getIndex(Model model) {
        final String welcomeMessage = "Welcome to your digital medical records!";
        model.addAttribute("welcome", welcomeMessage);

        return "index";
    }
}

