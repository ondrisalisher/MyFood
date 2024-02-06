package org.example.myfood.services;

import org.example.myfood.DTO.ProductDto;
import org.springframework.ui.Model;


public interface ProductService {
    public String addProduct(ProductDto productDto);

    public String deleteProduct(Long productId);

    public String updateProduct(Long productId, ProductDto productDto);

    public String updateProductPage(Long productId, Model model);

    public String productDetails(Long productId, Model model);

    public String products(Model model);

}
