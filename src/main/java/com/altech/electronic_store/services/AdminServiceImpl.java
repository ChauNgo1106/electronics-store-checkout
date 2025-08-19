package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public Deal addDeal(@RequestBody Deal deal) {
        return dealRepository.save(deal);
    }
}
