package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public String addUser(String first_name, String last_name, String username, String password, int desired_kkal, int desired_protein, int desired_carbohydrate, int desired_fat) {

        String password_encoded = passwordEncoder.encode(password);
        String role = "ROLE_USER";
        UserModel user = new UserModel(first_name, last_name, username, password_encoded, role, desired_kkal, desired_protein, desired_carbohydrate, desired_fat);
        userRepository.save(user);

        return "redirect:/user/login";
    }
}
