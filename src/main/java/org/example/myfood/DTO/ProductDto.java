package org.example.myfood.DTO;


public record ProductDto(
        String name,
        int calories,
        int protein,
        int carbohydrate,
        int fat,
        Long confirmedBy_id,
        String status
) {
}
