package com.inventorymanagement.controller;

import com.inventorymanagement.model.User;
import com.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setActive(false);
        user.setRole("user");
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "Utworzono nowego użytkownika. Poczekaj na aktywację przez Administratora...");

        // Przekierowanie do strony logowania
        return "redirect:/login";
    }

}

