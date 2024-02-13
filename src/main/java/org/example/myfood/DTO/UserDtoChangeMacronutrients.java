package org.example.myfood.DTO;

public record UserDtoChangeMacronutrients(
        int desired_calories,
        int desired_protein,
        int desired_carbohydrate,
        int desired_fat
) {
}
