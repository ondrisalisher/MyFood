package org.example.myfood.repositories;

import org.example.myfood.models.EatenModel;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


public interface EatenRepository extends CrudRepository<EatenModel,Long>{
    Iterable<EatenModel> findByUserIdAndDate(UserModel user, LocalDate date);

    EatenModel findByUserIdAndProductId(UserModel userModel, ProductModel productModel);
}
