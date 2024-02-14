package org.example.myfood.repositories;

import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductModel,Long>, JpaSpecificationExecutor<ProductModel> {
    public Iterable<ProductModel> findByStatus(String status);

    public Iterable<ProductModel> findByCreatedBy(UserModel user);

    public Iterable<ProductModel> findByConfirmedBy(UserModel user);
}
