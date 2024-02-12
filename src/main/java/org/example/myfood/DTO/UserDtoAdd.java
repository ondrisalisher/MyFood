package org.example.myfood.DTO;

public record UserDtoAdd(
        String firstName,
        String lastName,
        String username,
        String password,
        int desired_calories,
        int desired_protein,
        int desired_carbohydrate,
        int desired_fat
        ) {
}
