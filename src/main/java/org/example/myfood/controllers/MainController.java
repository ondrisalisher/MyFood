package org.example.myfood.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {


    @GetMapping("/")
    public String home(Model model){
        return "home";
    }


    @GetMapping("/forAdmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String forAdmin(Model model){
        return "home";
    }


    @GetMapping("/forUser")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String forUser(Model model){
        return "home";
    }

}
