package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    UserRepository userRepository;

    @Override
    public String adminPanel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        if(!user.getRole().equals("ROLE_ADMIN")){
            return "redirect:/user/profile";
        }

        return "adminPanel";
    }

    @Override
    public String users(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        if(!user.getRole().equals("ROLE_ADMIN")){
            return "redirect:/user/profile";
        }

        Iterable<UserModel> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users";
    }

    @Override
    public String userDetails(Long userId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        if(!user.getRole().equals("ROLE_ADMIN")){
            return "redirect:/user/profile";
        }


        UserModel userModel = userRepository.findById(userId).get();
        model.addAttribute("user", userModel);

        return "userDetails";
    }

    @Override
    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "redirect:/admin/users";
    }
}
