package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDTO;
import org.example.myfood.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String addUser(@ModelAttribute UserDTO userDTO, Model model){
        return userService.addUser(userDTO);
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        return userService.login();
    }
}
