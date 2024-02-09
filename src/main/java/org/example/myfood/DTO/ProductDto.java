package org.example.myfood.DTO;

import lombok.Getter;
import lombok.Setter;


public record ProductDto(
        String name,
        int calories,
        int protein,
        int carbohydrate,
        int fat
) {
}
