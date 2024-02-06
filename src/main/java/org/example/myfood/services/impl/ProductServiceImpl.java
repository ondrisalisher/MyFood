package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.ProductDto;
import org.example.myfood.models.ProductModel;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;


    @Override
    public String addProduct(ProductDto productDto) {
        Date creation_date = new Date();


        ProductModel product = new ProductModel(productDto.name(),productDto.kkal(),productDto.protein(),productDto.carbohydrate(),productDto.fat(),creation_date);
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
        if(!productRepository.existsById(productId)){
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
        if(!productRepository.existsById(productId)){
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
}
