package org.example.myfood.services;

import org.springframework.ui.Model;


public interface ProductService {
    public String saveProduct(String name, int kkal, int protein, int carbohydrate, int fat, String count_by);

    public String deleteProduct(Long productId);

    public String updateProduct(Long productId, String name, int kkal, int protein, int carbohydrate, int fat, String count_by);

    public String updateProductPage(Long productId, Model model);

    public String productDetails(Long productId, Model model);

    public String products(Model model);

}
