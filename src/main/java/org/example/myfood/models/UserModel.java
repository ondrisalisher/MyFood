package org.example.myfood.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    transient private String passwordConfirm;

    private String role;

    private int desired_calories;

    private int desired_protein;

    private int desired_carbohydrate;

    private int desired_fat;

}
