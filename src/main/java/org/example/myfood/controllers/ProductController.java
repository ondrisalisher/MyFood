package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
import org.example.myfood.DTO.EatenDTO;
import org.example.myfood.DTO.ProductDto;
import org.example.myfood.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    //todo
    @GetMapping("")
    public String products(Model model){
        return productService.products(model);
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable(value = "id") Long productId, Model model){
        return productService.productDetails(productId, model);
    }

    @GetMapping("/{id}/admin")
    public String productDetailsAdmin(@PathVariable(value = "id") Long productId, Model model){
        return productService.productDetailsAdmin(productId, model);
    }

    @GetMapping("/{id}/confirmation")
    public String productConfirmation(@PathVariable(value = "id") Long productId, Model model){
        return productService.productConfirmation(productId, model);
    }

    @PostMapping("/{id}/confirm")
    public String productConfirm(@PathVariable(value = "id") Long productId, Model model){
        return productService.productConfirm(productId, model);
    }

    @GetMapping("/{id}/update")
    public String updateProductPage(@PathVariable(value = "id") Long productId, Model model){
        return productService.updateProductPage(productId,model);
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable(value = "id") Long productId, @ModelAttribute ProductDto productDto, Model model){

        return productService.updateProduct(productId, productDto);
    }


    @GetMapping("/add")
    public String addProductPage(Model model){
        return productService.addProduct(model);
    }


    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductDto productDto, Model model){
        return productService.addProduct(productDto);
    }



    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable(value = "id") Long productId){
        return productService.deleteProduct(productId);
    }

    @PostMapping("/{id}/like")
    public String likeProduct(@PathVariable(value = "id") Long productId){
        return productService.likeProduct(productId);
    }

    @PostMapping("/{id}/eat")
    public String eatProduct(@PathVariable(value = "id") Long productId, @ModelAttribute EatenDTO eatenDTO){
        return productService.eatProduct(productId, eatenDTO);
    }

    @GetMapping("/favorite")
    public String favorite(Model model){
        return productService.favorite(model);
    }
}