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


        ProductModel product = new ProductModel(productDto.name(), productDto.kkal(), productDto.protein(), productDto.carbohydrate(), productDto.fat(), creation_date);
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
        product.setKkal(productDto.kkal());
        product.setProtein(productDto.protein());
        product.setCarbohydrate(productDto.carbohydrate());
        product.setFat(productDto.fat());
        product.setUpdate_date(update_date);

        productRepository.save(product);

        return "redirect:/product/{id}";
    }

    @Override
    public String updateProductPage(Long productId, Model model) {
        if (!productRepository.existsById(productId)) {
            return "redirect:/product";
        }

        Optional<ProductModel> product = productRepository.findById(productId);
        ArrayList<ProductModel> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
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
    public String products(Model model) {
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

        FavoriteModel favorite = new FavoriteModel();
        favorite.setProductId(product);
        favorite.setUserId(user);

        favoriteRepository.save(favorite);

        return "redirect:/product/{id}";
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


}
