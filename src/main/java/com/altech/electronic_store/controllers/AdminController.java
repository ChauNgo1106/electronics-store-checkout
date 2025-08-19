package com.altech.electronic_store.controllers;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.services.AdminService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product saved = adminService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deals")
    public ResponseEntity<Deal> addDeal(@RequestBody Deal deal){
        Deal savedDeal = adminService.addDeal(deal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeal);
    }
}
