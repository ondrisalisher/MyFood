package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDtoAdd;
import org.example.myfood.DTO.UserDtoEditProfile;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public String addUser(UserDtoAdd userDTO) {

        String password_encoded = passwordEncoder.encode(userDTO.password());
        String role = "ROLE_USER";
        int desired_calories =userDTO.desired_calories();
        int desired_protein =userDTO.desired_protein();
        int desired_carbohydrate =userDTO.desired_carbohydrate();
        int desired_fat =userDTO.desired_fat();
        if (desired_calories <= 0){
            desired_calories = 1;
        }
        if (desired_protein <= 0){
            desired_protein = 1;
        }
        if (desired_carbohydrate <= 0){
            desired_carbohydrate = 1;
        }
        if (desired_fat <= 0){
            desired_fat = 1;
        }
        UserModel user = new UserModel(userDTO.firstName(), userDTO.lastName(), userDTO.username(), password_encoded, role, desired_calories, desired_protein, desired_carbohydrate, desired_fat);
        userRepository.save(user);

        return "redirect:/login";
    }

    @Override
    public String login() {
        return "login";
    }

    @Override
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @Override
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        model.addAttribute("user", user);
        return "editProfile";
    }

    @Override
    public String editProfile(Long userId, UserDtoEditProfile userDTO) {
        UserModel user = userRepository.findById(userId).get();
        user.setUsername(userDTO.username());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
//        user.setUsername(userDTO.username());
//        user.setUsername(userDTO.username());
        userRepository.save(user);
        return "redirect:/user/profile";
    }
}
