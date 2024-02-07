package org.example.myfood.repositories;

import org.example.myfood.models.FavoriteModel;
import org.springframework.data.repository.CrudRepository;

public interface    FavoriteRepository extends CrudRepository<FavoriteModel,Long>{
}
