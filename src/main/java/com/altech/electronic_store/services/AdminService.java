package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;

public interface AdminService {
    Product createProduct(Product product);
    void deleteProduct(Long id);
    Deal addDeal(Deal deal);
}
