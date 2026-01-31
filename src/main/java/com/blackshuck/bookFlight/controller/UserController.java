package com.blackshuck.bookFlight.controller;

import com.blackshuck.bookFlight.entity.User;
import com.blackshuck.bookFlight.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model)
    {
        model.addAttribute("user", new User());

        return "sign-up";
    }

    @PostMapping("/sign-up/new")
    public String userSignUp(@Valid User user, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "sign-up";
        }

        if (userService.emailRepetitionError(user.getEmail()))
        {
            bindingResult.rejectValue("email", "error.user",
                    "This email is already registered");

            return "sign-up";
        }

        userService.addNew(user);

        System.out.println("user: " + user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String logIn(@RequestParam(value = "error", required = false) String error, Model model)
    {
        if (error != null)
        {
            model.addAttribute("loginError", "The email or password is incorrect");
        }

        return "login";
    }

    @PostMapping("/logout")
    public String Logout(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null)
        {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

}
