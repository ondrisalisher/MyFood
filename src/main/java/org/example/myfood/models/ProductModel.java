package org.example.myfood.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserModel createdBy;

    @ManyToOne
    @JoinColumn(name = "confirmed_by")
    private UserModel confirmedBy;

    @NonNull
    private Date creationDate;
    private Date confirmationDate;
    private Date updateDate;

    @NonNull
    private String status;
}
