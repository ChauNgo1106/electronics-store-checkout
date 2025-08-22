package com.altech.electronic_store.controllers;

import com.altech.electronic_store.dto.request.BasketItemRequest;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.model.Receipt;
import com.altech.electronic_store.services.BasketService;
import com.altech.electronic_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private BasketService basketService;
    @Autowired
    private ProductService productService;

    @PostMapping("/basket")
    public ResponseEntity<Void> add(@RequestBody BasketItemRequest basketItemRequest) {
        basketService.addToBasket(basketItemRequest.getProductId(), basketItemRequest.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/basket/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        basketService.removeFromBasket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(pageable));
    }

    @GetMapping("/receipt")
    public Receipt getReceipt() {
        return basketService.calculateReceipt();
    }
}
