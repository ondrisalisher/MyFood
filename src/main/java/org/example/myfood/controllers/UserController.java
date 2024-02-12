package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDtoAdd;
import org.example.myfood.DTO.UserDtoEditProfile;
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
    public String addUser(@ModelAttribute UserDtoAdd userDTO, Model model){
        return userService.addUser(userDTO);
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        return userService.login();
    }

    @GetMapping("/profile")
    public String profile(Model model){
        return userService.profile(model);
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model){
        return userService.editProfile(model);
    }

    @PostMapping("/{id}/edit")
    public String editProfile(@PathVariable(value = "id") Long userId, UserDtoEditProfile userDTO, Model model){
        return userService.editProfile(userId, userDTO);
    }
}
