package org.example.myfood.services;

import org.example.myfood.DTO.EatenDTO;
import org.example.myfood.DTO.ProductDto;
import org.example.myfood.DTO.ProductDtoPage;
import org.example.myfood.DTO.ProductDtoProducts;
import org.springframework.ui.Model;


public interface ProductService {
    public String addProduct(ProductDto productDto);

    public  String addProduct(Model model);

    public  String productConfirmation(Long productId, Model model);

    public  String productConfirm(Long productId, Model model);

    public String deleteProduct(Long productId);

    public String updateProduct(Long productId, ProductDto productDto);

    public String updateProductPage(Long productId, Model model);

    public String productDetails(Long productId, Model model);

    public String products(Model model, ProductDtoProducts productDtoProducts);

    public String likeProduct(Long productId);

    public String deleteProductFromFavorite(Long productId);

    public String eatProduct(Long productId, EatenDTO eatenDTO);

    public String favorite(Model model);
}
