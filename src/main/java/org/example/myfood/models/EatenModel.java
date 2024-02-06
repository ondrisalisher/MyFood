package org.example.myfood.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EatenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel productId;

    private int quantity;

    private Date dateTime;
}

