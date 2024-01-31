package org.example.myfood.controllers;

import lombok.AllArgsConstructor;
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

    @GetMapping("/{id}/update")
    public String updateProductPage(@PathVariable(value = "id") Long productId, Model model){
        return productService.updateProductPage(productId,model);
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable(value = "id") Long productId, @RequestParam String name, @RequestParam int kkal, @RequestParam int protein, @RequestParam int carbohydrate, @RequestParam int fat, @RequestParam String count_by, Model model){

        return productService.updateProduct(productId,name,kkal,protein,carbohydrate,fat,count_by);
    }


    @GetMapping("/add")
    public String addProductPage(Model model){
        return "addProduct";
    }


    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam int kkal, @RequestParam int protein, @RequestParam int carbohydrate, @RequestParam int fat, @RequestParam String count_by, Model model){
        return productService.saveProduct(name,kkal,protein,carbohydrate,fat,count_by);
    }



    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable(value = "id") Long productId){
        return productService.deleteProduct(productId);
    }
}