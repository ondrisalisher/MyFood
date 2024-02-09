package org.example.myfood.DTO;

import org.springframework.web.bind.annotation.RequestParam;

public record UserDTO(
        String first_name,
        String last_name,
        String username,
        String password,
        int desired_calories,
        int desired_protein,
        int desired_carbohydrate,
        int desired_fat
        ) {
}
