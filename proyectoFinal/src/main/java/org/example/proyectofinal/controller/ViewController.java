package org.example.proyectofinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping()
    public String landingPage(Model model){
        model.addAttribute("baseUrl", "Hola");

        return "landingPage";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("baseUrl", "Hola");

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        model.addAttribute("baseUrl", "Hola");

        return "signup";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        model.addAttribute("baseUrl", "Hola");

        return "home";
    }
}
