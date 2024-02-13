package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.EatenDTO;
import org.example.myfood.DTO.ProductDto;
import org.example.myfood.models.EatenModel;
import org.example.myfood.models.FavoriteModel;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.FavoriteRepository;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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

        Optional<ProductModel> product = productRepository.findById(productId);
        ArrayList<ProductModel> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);

        return "productConfirmation";
    }

    @Override
    public String productConfirm(Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        ProductModel product = productRepository.findById(productId).orElseThrow();

        product.setConfirmedBy(user);
        product.setStatus("confirmed");
        productRepository.save(product);

        return "redirect:/product";
    }

    @Override
    public String deleteProduct(Long productId) {
        productRepository.deleteById(productId);
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

        return "redirect:/product/{id}";
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

        Optional<ProductModel> product = productRepository.findById(productId);
        ArrayList<ProductModel> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);

        return "productDetails";
    }

    @Override
    public String productDetailsAdmin(Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        if(user.getRole().equals("ROLE_USER")){
            return "redirect:/product/"+productId;
        }

        if (!productRepository.existsById(productId)) {
            return "redirect:/product";
        }

        Optional<ProductModel> product = productRepository.findById(productId);
        ArrayList<ProductModel> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        //todo
        model.addAttribute("Likes", 0);

        return "productDetailsAdmin";
    }

    @Override
    public String products(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        if(user.getRole().equals("ROLE_ADMIN")){
            Iterable<ProductModel> unconfirmed = productRepository.findByStatus("unconfirmed");
            model.addAttribute("unconfirmed", unconfirmed);
            Iterable<ProductModel> confirmed = productRepository.findByStatus("confirmed");
            model.addAttribute("confirmed", confirmed);

            model.addAttribute("forAdmin", "/admin");
        }
        else if(user.getRole().equals("ROLE_USER")){
            Iterable<ProductModel> confirmed = productRepository.findByStatus("confirmed");
            model.addAttribute("confirmed", confirmed);

            model.addAttribute("forAdmin", "");
        }


        Iterable<ProductModel> products = productRepository.findAll();
        model.addAttribute("products", products);

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
