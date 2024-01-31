package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/signUp")
    public String addUserPage(Model model){
        return "signUp";
    }


    @PostMapping("/signUp")
    public String addUser(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String username, @RequestParam String password, @RequestParam int desired_kkal, @RequestParam int desired_protein, @RequestParam int desired_carbohydrate, @RequestParam int desired_fat, Model model){
        return userService.addUser(first_name, last_name, username, password, desired_kkal, desired_protein, desired_carbohydrate, desired_fat);
    }

    @GetMapping("/login")
    public String logInPage(Model model){
        return "login";
    }
}
