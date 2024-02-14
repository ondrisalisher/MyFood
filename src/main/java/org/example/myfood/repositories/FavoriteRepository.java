package org.example.myfood.repositories;

import org.example.myfood.models.FavoriteModel;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface    FavoriteRepository extends CrudRepository<FavoriteModel,Long>{
    Iterable<FavoriteModel> findByUserId(UserModel userId);
    boolean existsByUserIdAndProductId(UserModel user, ProductModel product);

    Optional<FavoriteModel> findByUserIdAndProductId(UserModel user, ProductModel product);

    Iterable<FavoriteModel> findByProductId(ProductModel product);

    void deleteByProductId(ProductModel product);

    void deleteByUserId(UserModel user);

}
