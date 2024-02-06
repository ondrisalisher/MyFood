package org.example.myfood.DTO;

import lombok.Getter;
import lombok.Setter;


public record ProductDto(
        String name,
        int kkal,
        int protein,
        int carbohydrate,
        int fat
) {
}
