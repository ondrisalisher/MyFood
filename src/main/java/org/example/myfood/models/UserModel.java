package org.example.myfood.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String role;
    @NonNull
    private int desired_calories;
    @NonNull
    private int desired_protein;
    @NonNull
    private int desired_carbohydrate;
    @NonNull
    private int desired_fat;

}
