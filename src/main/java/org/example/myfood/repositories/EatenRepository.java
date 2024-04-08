package org.example.myfood.repositories;

import org.example.myfood.models.EatenModel;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface EatenRepository extends CrudRepository<EatenModel,Long>{
    Iterable<EatenModel> findByUserIdAndDate(UserModel user, LocalDate date);

    EatenModel findByUserIdAndProductId(UserModel userModel, ProductModel productModel);

    void deleteByProductId(ProductModel product);

    void deleteByUserId(UserModel user);


    @Query("select sum(p.calories * e.quantity / 100), sum(p.protein * e.quantity / 100), sum(p.carbohydrate * e.quantity / 100), sum(p.fat * e.quantity / 100) from ProductModel p join EatenModel e ON e.productId.id = p.id where e.date between :firstDate and :lastDate")
    String getReport(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate) ;
}
