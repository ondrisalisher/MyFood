package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    AdminService adminService;

    @GetMapping("")
    public String adminPanel(Model model){
        return adminService.adminPanel();
    }

    @GetMapping("/users")
    public String users(Model model){
        return adminService.users(model);
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable(value = "id")Long userId, Model model){
        return adminService.userDetails(userId, model);
    }

    @PostMapping("/user/{id}/delete")
    public String userDelete(@PathVariable(value = "id")Long userId, Model model){
        return adminService.deleteUser(userId);
    }
}
