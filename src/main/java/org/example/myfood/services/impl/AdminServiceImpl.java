package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.UserDtoChangeRole;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.FavoriteRepository;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    UserRepository userRepository;
    FavoriteRepository favoriteRepository;
    EatenRepository eatenRepository;
    ProductRepository productRepository;

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
    @Transactional
    public String deleteUser(Long userId) {
        UserModel user = userRepository.findById(userId).get();
        Iterable<ProductModel> createdBy = productRepository.findByCreatedBy(user);
        Iterable<ProductModel> confirmedBy = productRepository.findByConfirmedBy(user);
        for(ProductModel product:createdBy){
            product.setCreatedBy(null);
        }
        for(ProductModel product:confirmedBy){
            product.setConfirmedBy(null);
        }
        eatenRepository.deleteByUserId(user);
        favoriteRepository.deleteByUserId(user);
        userRepository.delete(user);
        return "redirect:/admin/user";
    }

    @Override
    public String changeRole(Long userId, UserDtoChangeRole userDto) {
        UserModel user = userRepository.findById(userId).get();
        if(userDto.role().equals("toAdmin")){
            user.setRole("ROLE_ADMIN");
        }else{
            user.setRole("ROLE_USER");
        }
        userRepository.save(user);
        return "redirect:/admin/user/"+userId;
    }
}
