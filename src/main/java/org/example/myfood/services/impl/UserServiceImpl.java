package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDTO;
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
    public String addUser(UserDTO userDTO) {

        String password_encoded = passwordEncoder.encode(userDTO.password());
        String role = "ROLE_USER";
        UserModel user = new UserModel(userDTO.first_name(), userDTO.last_name(), userDTO.username(), password_encoded, role, userDTO.desired_calories(), userDTO.desired_protein(), userDTO.desired_carbohydrate(), userDTO.desired_fat());
        userRepository.save(user);

        return "redirect:/";
    }
}
