package com.altech.electronic_store.services.impl;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    private final ProductRepository productRepository;
    private final DealRepository dealRepository;

    @Autowired
    AdminServiceImpl(ProductRepository productRepository, DealRepository dealRepository){
        this.productRepository = productRepository;
        this.dealRepository = dealRepository;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Deal addDeal(Long productId, String type, LocalDateTime expiration){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
        Deal deal = Deal.builder()
                .product(product)
                .type(type)
                .expiration(expiration)
                .build();
        return dealRepository.save(deal);
    }
}
