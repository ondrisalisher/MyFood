package org.example.myfood.services.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.example.myfood.DTO.AdminDtoUsers;
import org.example.myfood.DTO.UserDtoChangeRole;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.FavoriteRepository;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public String users(Model model, AdminDtoUsers adminDtoUsers) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        final Long currentUserId = user.getId();
        if(!user.getRole().equals("ROLE_ADMIN")){
            return "redirect:/user/profile";
        }

        int pageNumber;
        int pageSize;
        if(adminDtoUsers.pageNumber() == null && adminDtoUsers.pageSize() == null){
            pageNumber = 0;
            pageSize = 3;
        }else{
            pageNumber = Integer.parseInt(adminDtoUsers.pageNumber());
            pageSize = Integer.parseInt(adminDtoUsers.pageSize());
        }


        String searched = "";

        if(!(adminDtoUsers.search() == null)) {
            searched = adminDtoUsers.search();
        }

        String finalSearched = searched;
        Specification<UserModel> search = new Specification<UserModel>() {
            @Override
            public Predicate toPredicate(Root<UserModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate usernamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")), "%" + finalSearched.toLowerCase() + "%"
                );
                Predicate excludeCurrentUserPredicate = criteriaBuilder.notEqual(root.get("id"), currentUserId);
                return criteriaBuilder.and(usernamePredicate, excludeCurrentUserPredicate);
            }
        };


        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserModel> users = userRepository.findAll(search, pageable);

        model.addAttribute("users", users);

        model.addAttribute("searched", searched);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

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
