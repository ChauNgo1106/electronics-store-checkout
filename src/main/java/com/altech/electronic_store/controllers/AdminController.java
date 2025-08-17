package com.altech.electronic_store.controllers;

import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product saved = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
