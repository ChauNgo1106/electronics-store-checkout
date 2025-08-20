package com.altech.electronic_store.controllers;

import com.altech.electronic_store.model.BasketItem;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/basket")
    public ResponseEntity<BasketItem> addToBasket(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(customerService.addToBasket(productId, quantity));
    }

    @DeleteMapping("/basket/{id}")
    public ResponseEntity<Void> removeFromBasket(@PathVariable Long id) {
        customerService.removeFromBasket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> filterProducts(@RequestParam(required = false) String category,
                                                        @RequestParam(required = false) Double minPrice,
                                                        @RequestParam(required = false) Double maxPrice,
                                                        @RequestParam(required = false) Boolean available) {
        return ResponseEntity.ok(customerService.filterProducts(category, minPrice, maxPrice, available));
    }
}
