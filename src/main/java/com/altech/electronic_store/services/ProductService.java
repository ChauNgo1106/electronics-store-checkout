package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(Product product);
    void deleteProduct(Long id);
}
