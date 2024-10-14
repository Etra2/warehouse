package com.inventorymanagement.controller;

import com.inventorymanagement.model.User;
import com.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Właściwy import z Springa
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username);
        // Sprawdzenie hasła (pamiętaj o dodaniu szyfrowania haseł w produkcyjnych aplikacjach)
        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/products";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/login";
    }
}

