package org.example.myfood.models;


import jakarta.persistence.*;
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
    private int calories;
    @NonNull
    private int protein;
    @NonNull
    private int carbohydrate;
    @NonNull
    private int fat;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserModel createdBy;
    @NonNull
    private Date creationDate;
    private Date updateDate;
    //todo
    private String status;
}
