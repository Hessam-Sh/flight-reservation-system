package com.blackshuck.bookFlight.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String homePage(HttpSession httpSession, Model model)
    {
        String welcomeForLogin = (String) httpSession.getAttribute("loginMessage");

        model.addAttribute("welcomeForLogin", welcomeForLogin);

        httpSession.removeAttribute("loginMessage");

        return "index";
    }
}
