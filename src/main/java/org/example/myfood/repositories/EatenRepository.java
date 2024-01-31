package org.example.myfood.repositories;

import org.example.myfood.models.EatenModel;
import org.springframework.data.repository.CrudRepository;

public interface EatenRepository extends CrudRepository<EatenModel,Long>{
}
