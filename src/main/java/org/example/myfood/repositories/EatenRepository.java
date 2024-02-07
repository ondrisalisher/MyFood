package org.example.myfood.repositories;

import org.example.myfood.models.EatenModel;
import org.example.myfood.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface EatenRepository extends CrudRepository<EatenModel,Long>{
    Iterable<EatenModel> findByUserId(UserModel userId);
}
