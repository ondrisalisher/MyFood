package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.myfood.services.HomeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class MainController {
    private final HomeService homeService;

    @GetMapping("/")
    public String home(Model model){
        return homeService.home(model);
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


    public String header(Model model){
        return "";
    }
}
