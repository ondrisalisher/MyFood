package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.AdminDtoUsers;
import org.example.myfood.DTO.UserDtoChangeRole;
import org.example.myfood.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    AdminService adminService;

    @GetMapping("")
    public String adminPanel(Model model){
        return adminService.adminPanel();
    }

    @GetMapping("/user")
    public String users(Model model, AdminDtoUsers adminDtoUsers){
        return adminService.users(model, adminDtoUsers);
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable(value = "id")Long userId, Model model){
        return adminService.userDetails(userId, model);
    }

    @PostMapping("/user/{id}/delete")
    public String userDelete(@PathVariable(value = "id")Long userId, Model model){
        return adminService.deleteUser(userId);
    }

    @PostMapping("/user/{id}/changeRole")
    public String changeRole(@PathVariable(value = "id") Long userId, @ModelAttribute UserDtoChangeRole userDto){
        return adminService.changeRole(userId, userDto);
    }
}
