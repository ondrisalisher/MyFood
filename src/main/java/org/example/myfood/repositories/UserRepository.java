package org.example.myfood.repositories;

import org.example.myfood.models.UserModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel,Long>, JpaSpecificationExecutor<UserModel> {
    Optional<UserModel> findByUsername(String username);
}
