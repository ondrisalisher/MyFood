package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
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
    public String saveProduct(String name, int kkal, int protein, int carbohydrate, int fat, String count_by) {
        Date creation_date = new Date();

        boolean is_piece;
        if (count_by.equals("piece")){
            is_piece = true;
        } else if (count_by.equals("weight")) {
            is_piece = false;
        }else {
            is_piece= Boolean.parseBoolean(null);
        }



        ProductModel product = new ProductModel(name,kkal,protein,carbohydrate,fat,is_piece,creation_date);
        productRepository.save(product);

        return "redirect:/product";
    }

    @Override
    public String deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return "redirect:/product";
    }

    @Override
    public String updateProduct(Long productId, String name, int kkal, int protein, int carbohydrate, int fat, String count_by) {
        Date update_date = new Date();

        boolean is_piece;
        if (count_by.equals("piece")){
            is_piece = true;
        } else if (count_by.equals("weight")) {
            is_piece = false;
        }else {
            is_piece= Boolean.parseBoolean(null);
        }



        ProductModel product = productRepository.findById(productId).orElseThrow();
        product.setName(name);
        product.setKkal(kkal);
        product.setProtein(protein);
        product.setCarbohydrate(carbohydrate);
        product.setFat(fat);
        product.set_piece(is_piece);
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
