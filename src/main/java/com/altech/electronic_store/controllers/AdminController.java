package com.altech.electronic_store.controllers;

import com.altech.electronic_store.dto.mapping.DealMapping;
import com.altech.electronic_store.dto.mapping.ProductMapping;
import com.altech.electronic_store.dto.request.DealRequest;
import com.altech.electronic_store.dto.request.ProductRequest;
import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest){
        Product product = ProductMapping.toProduct(productRequest);
        Product saved = adminService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllProducts(pageable));
    }


    @PostMapping("/deals")
    public ResponseEntity<Deal> addDeal(@RequestParam Long productId,
                                        @RequestParam String type,
                                        @RequestParam LocalDateTime expiration) {
        return ResponseEntity.ok(adminService.addDeal(productId, type, expiration));
    }
}
