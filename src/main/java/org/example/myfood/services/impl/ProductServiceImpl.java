package org.example.myfood.services.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.example.myfood.DTO.EatenDTO;
import org.example.myfood.DTO.ProductDto;
import org.example.myfood.DTO.ProductDtoPage;
import org.example.myfood.DTO.ProductDtoProducts;
import org.example.myfood.models.EatenModel;
import org.example.myfood.models.FavoriteModel;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.FavoriteRepository;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private FavoriteRepository favoriteRepository;
    private UserRepository userRepository;
    private EatenRepository eatenRepository;


    @Override
    public String addProduct(ProductDto productDto) {
        Date creation_date = new Date();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        ProductModel product = new ProductModel();

        product.setName(productDto.name());
        product.setCalories(productDto.calories());
        product.setProtein(productDto.protein());
        product.setCarbohydrate(productDto.carbohydrate());
        product.setFat(productDto.fat());
        product.setCreatedBy(user);
        product.setCreationDate(creation_date);
        try {
            product.setConfirmedBy(userRepository.findById(productDto.confirmedBy_id()).get());
        }catch (Exception e){

        }

        product.setStatus(productDto.status());
        if(productDto.status().equals("confirmed")){
            product.setConfirmationDate(creation_date);
        }
        productRepository.save(product);

        return "redirect:/product";
    }

    @Override
    public String addProduct(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        if(user.getRole().equals("ROLE_ADMIN")){
            model.addAttribute("status", "confirmed");
            model.addAttribute("confirmedBy", user.getId());
        }
        else if(user.getRole().equals("ROLE_USER")){
            model.addAttribute("status", "unconfirmed");
        }else{
            model.addAttribute("status", "none");
        }
        return "addProduct";
    }

    @Override
    public String productConfirmation(Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        if(user.getRole().equals("ROLE_USER")){
            return "redirect:/product";
        }

        if (!productRepository.existsById(productId)) {
            return "redirect:/product";
        }

        ProductModel product = productRepository.findById(productId).get();
        model.addAttribute("product", product);

        return "productConfirmation";
    }

    @Override
    public String productConfirm(Long productId, Model model) {
        Date confirmationDate = new Date();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        ProductModel product = productRepository.findById(productId).orElseThrow();

        product.setConfirmedBy(user);
        product.setStatus("confirmed");
        product.setConfirmationDate(confirmationDate);
        productRepository.save(product);

        return "redirect:/product";
    }

    @Override
    @Transactional
    public String deleteProduct(Long productId) {
        ProductModel product = productRepository.findById(productId).get();
        favoriteRepository.deleteByProductId(product);
        eatenRepository.deleteByProductId(product);
        productRepository.delete(product);
        return "redirect:/product";
    }

    @Override
    public String updateProduct(Long productId, ProductDto productDto) {
        Date update_date = new Date();


        ProductModel product = productRepository.findById(productId).orElseThrow();
        product.setName(productDto.name());
        product.setCalories(productDto.calories());
        product.setProtein(productDto.protein());
        product.setCarbohydrate(productDto.carbohydrate());
        product.setFat(productDto.fat());
        product.setUpdateDate(update_date);

        productRepository.save(product);
        if (product.getStatus().equals("confirmed")){
            return "redirect:/product/{id}";
        }
        else {
            return "redirect:/product/" + productId + "/confirmation";
        }

    }

    @Override
    public String updateProductPage(Long productId, Model model) {

        if (!productRepository.existsById(productId)) {
            return "redirect:/product";
        }

        ProductModel product = productRepository.findById(productId).get();
        model.addAttribute("product", product);

        return "editProduct";
    }

    @Override
    public String productDetails(Long productId, Model model) {

        if (!productRepository.existsById(productId)) {
            return "redirect:/product";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        if(user.getRole().equals("ROLE_ADMIN")){
            ProductModel product = productRepository.findById(productId).get();
            model.addAttribute("product", product);

            Iterable<FavoriteModel> liked = favoriteRepository.findByProductId(product);
            int likes = 0;
            for(FavoriteModel element: liked){
                likes = likes + 1;
            }
            model.addAttribute("likes", likes);

            return "productDetailsAdmin";
        }

        if(user.getRole().equals("ROLE_USER")){
            ProductModel product = productRepository.findById(productId).get();
            model.addAttribute("product", product);

            Iterable<FavoriteModel> liked = favoriteRepository.findByProductId(product);
            int likes = 0;
            for(FavoriteModel element: liked){
                likes = likes + 1;
            }
            model.addAttribute("likes", likes);

            return "productDetails";
        }

        return null;
    }

    @Override
    public String products(Model model, ProductDtoProducts productDtoProducts) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }


        int pageNumber;
        int pageSize;
        if(productDtoProducts.pageNumber() == null && productDtoProducts.pageSize() == null){
            pageNumber = 0;
            pageSize = 3;
        }else{
            pageNumber = Integer.parseInt(productDtoProducts.pageNumber());
            pageSize = Integer.parseInt(productDtoProducts.pageSize());
        }


        String searched = "";

        if(!(productDtoProducts.search() == null)) {
            searched = productDtoProducts.search();
        }

        String finalSearched = searched;
        Specification<ProductModel> search = new Specification<ProductModel>() {
            @Override
            public Predicate toPredicate(Root<ProductModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + finalSearched.toLowerCase() + "%");
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), "confirmed");
                return criteriaBuilder.and(namePredicate, statusPredicate);
            }
        };


        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductModel> confirmedPage = productRepository.findAll(search, pageable);

        if(user.getRole().equals("ROLE_ADMIN")){
            Iterable<ProductModel> unconfirmed = productRepository.findByStatus("unconfirmed");
            model.addAttribute("unconfirmed", unconfirmed);
            model.addAttribute("confirmed", confirmedPage);
        }
        else if(user.getRole().equals("ROLE_USER")){
            model.addAttribute("confirmed", confirmedPage);

        }

        model.addAttribute("searched", searched);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

        return "products";
    }

    @Override
    public String likeProduct(Long productId) {
        Long userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        ProductModel product = productRepository.findById(productId).get();

        if (user != null && product != null) {
            boolean exists = favoriteRepository.existsByUserIdAndProductId(user, product);
            if (!exists) {
                FavoriteModel favorite = new FavoriteModel();
                favorite.setProductId(product);
                favorite.setUserId(user);

                favoriteRepository.save(favorite);
            }
        }

        return "redirect:/product/"+ productId;
    }

    @Override
    public String deleteProductFromFavorite(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        ProductModel product = productRepository.findById(productId).get();

        FavoriteModel favorie = favoriteRepository.findByUserIdAndProductId(user, product).get();

        favoriteRepository.delete(favorie);

        return "redirect:/product/favorite";
    }

    @Override
    public String eatProduct(Long productId, EatenDTO eatenDTO) {
        Date eaten_date = new Date();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        ProductModel product = productRepository.findById(productId).get();

        EatenModel eaten = new EatenModel();
        eaten.setProductId(product);
        eaten.setUserId(user);
        eaten.setQuantity(eatenDTO.quantity());
        eaten.setDateTime(eaten_date);
        eaten.setDate(eaten_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        eatenRepository.save(eaten);

        return "redirect:/product";
    }

    @Override
    public String favorite(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        Iterable<FavoriteModel> favoriteProducts = favoriteRepository.findByUserId(user);
        model.addAttribute("favoriteProducts", favoriteProducts);

        return "favorite";
    }


}
