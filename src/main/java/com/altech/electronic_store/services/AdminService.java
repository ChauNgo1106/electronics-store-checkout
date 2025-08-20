package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdminService {
    Product createProduct(Product product);
    void deleteProduct(Long id);
    Page<Product> getAllProducts(Pageable pageable);
    Deal addDeal(Long productId, String type, LocalDateTime expiration);
}
