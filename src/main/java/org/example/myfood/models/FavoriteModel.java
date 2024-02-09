package org.example.myfood.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class FavoriteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "product")
    private ProductModel productId;

}
