package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.services.EatenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eaten")
@AllArgsConstructor
public class EatenController {

    private EatenService eatenService;

    @GetMapping("/")
    public String eaten(Model model){
        return eatenService.eaten(model);
    }
}
