package com.altech.electronic_store.controllers;

import com.altech.electronic_store.dto.mapping.DealMapping;
import com.altech.electronic_store.dto.mapping.ProductMapping;
import com.altech.electronic_store.dto.request.DealRequest;
import com.altech.electronic_store.dto.request.ProductRequest;
import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.services.DealService;
import com.altech.electronic_store.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "Admin", description = "Admin operation APIs")
@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminController {

    private final ProductService productService;
    private final DealService dealService;

    public AdminController(ProductService productService, DealService dealService){
        this.productService = productService;
        this.dealService = dealService;
    }

    @Operation(description = "Create a new product")
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest){
        Product product = ProductMapping.toProduct(productRequest);
        Product saved = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(description = "Delete a the existing product by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Get products in pagination")
    @GetMapping()
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(pageable));
    }

    @Operation(description = "Add a deal to a product")
    @PostMapping("/deals")
    public ResponseEntity<Deal> addDeal(@RequestBody DealRequest dealRequest) {
        Deal deal = DealMapping.toDeal(dealRequest);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dealService.addDeal(deal));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
