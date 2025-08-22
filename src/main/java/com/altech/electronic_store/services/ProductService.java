package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {
    Product createProduct(Product product);
    void deleteProduct(Long id);
    Page<Product> getAllProducts(Pageable pageable);
    Page<Product> filterProducts(String category, Double minPrice, Double maxPrice, boolean available, int page, int size);
}
