package org.example.myfood.repositories;

import org.example.myfood.models.ProductModel;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductModel,Long> {
}
