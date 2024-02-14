package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.services.EatenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eaten")
@AllArgsConstructor
public class EatenController {

    private EatenService eatenService;

    @GetMapping("/{day}-{month}-{year}")
    public String eaten(@PathVariable(value = "day") int day, @PathVariable(value = "month") int month, @PathVariable(value = "year") int year, Model model){
        return eatenService.eaten(day, month, year ,model);
    }

    @GetMapping("/{id}/details")
    public String eatenDetails(@PathVariable(value = "id") Long eatenId, Model model){
        return eatenService.eatenDetails(eatenId, model);
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable(value = "id") Long eatenId, Model model){
        return eatenService.deleteProduct(eatenId);
    }

    @PostMapping("/{id}/like")
    public String likeProduct(@PathVariable(value = "id") Long productId) {
        return eatenService.likeProduct(productId);
    }
}
