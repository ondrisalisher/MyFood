package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDtoAdd;
import org.example.myfood.DTO.UserDtoChangeMacronutrients;
import org.example.myfood.DTO.UserDtoChangePassword;
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

    @GetMapping("/profile/changePassword")
    public String changePassword(Model model){
        return userService.changePassword(model);
    }

    @PostMapping("/{id}/changePassword")
    public String changePassword(@PathVariable(value = "id") Long userId, UserDtoChangePassword userDTO, Model model){
        return userService.changePassword(userId, userDTO, model);
    }

    @GetMapping("/profile/changeMacronutrients")
    public String changeMacronutrients(Model model){
        return userService.changeMacronutrients(model);
    }

    @PostMapping("/{id}/changeMacronutrients")
    public String changeMacronutrients(@PathVariable(value = "id") Long userId, UserDtoChangeMacronutrients userDTO, Model model){
        return userService.changeMacronutrients(userId, userDTO, model);
    }
}
