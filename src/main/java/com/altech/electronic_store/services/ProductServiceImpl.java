package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
