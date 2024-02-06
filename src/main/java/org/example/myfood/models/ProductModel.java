package org.example.myfood.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private int kkal;
    @NonNull
    private int protein;
    @NonNull
    private int carbohydrate;
    @NonNull
    private int fat;
    //todo
    private String created_by;
    @NonNull
    private Date creation_date;
    private Date update_date;
    //todo
    private String status;
}
