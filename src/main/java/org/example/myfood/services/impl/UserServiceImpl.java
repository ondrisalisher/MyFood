package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDtoAdd;
import org.example.myfood.DTO.UserDtoChangeMacronutrients;
import org.example.myfood.DTO.UserDtoChangePassword;
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
        if(!userDTO.password().equals(userDTO.passwordConfirm())){
            return "redirect:/user/signUp";
        }
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
        UserModel user = new UserModel();
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setUsername(userDTO.username());
        user.setPassword(password_encoded);
        user.setRole(role);
        user.setDesired_calories(desired_calories);
        user.setDesired_protein(desired_protein);
        user.setDesired_carbohydrate(desired_carbohydrate);
        user.setDesired_fat(desired_fat);
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
        if(user.getRole().equals("ROLE_ADMIN")){
            model.addAttribute("hidden", "false");
        }
        else{
            model.addAttribute("hidden", "hidden");
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
        userRepository.save(user);
        return "redirect:/user/login";
    }

    @Override
    public String changePassword(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        model.addAttribute("user", user);
        return "changePassword";
    }

    @Override
    public String changePassword(Long userId, UserDtoChangePassword userDTO, Model model) {
        UserModel user = userRepository.findById(userId).get();

        if(!passwordEncoder.matches(userDTO.oldPassword(), user.getPassword())){
            model.addAttribute("error", "Old password is incorrect");
            model.addAttribute("user", user);
            return "changePassword";
        }

        if(!userDTO.password().equals(userDTO.passwordConfirm())){
            model.addAttribute("error", "New password and confirmation do not match");
            model.addAttribute("user", user);
            return "changePassword";
        }



        String passwordEncoded = passwordEncoder.encode(userDTO.password());
        user.setPassword(passwordEncoded);
        userRepository.save(user);

        return "redirect:/user/profile";
    }

    @Override
    public String changeMacronutrients(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        model.addAttribute("user", user);
        return "changeMacronutrients";
    }

    @Override
    public String changeMacronutrients(Long userId, UserDtoChangeMacronutrients userDTO, Model model) {
        UserModel user = userRepository.findById(userId).get();

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

        user.setDesired_calories(desired_calories);
        user.setDesired_protein(desired_protein);
        user.setDesired_carbohydrate(desired_carbohydrate);
        user.setDesired_fat(desired_fat);
        userRepository.save(user);

        return "redirect:/user/profile";
    }
}
