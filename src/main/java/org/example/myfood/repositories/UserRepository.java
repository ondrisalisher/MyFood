package org.example.myfood.repositories;

import org.example.myfood.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel,Long> {
    Optional<UserModel> findByUsername(String username);
}
