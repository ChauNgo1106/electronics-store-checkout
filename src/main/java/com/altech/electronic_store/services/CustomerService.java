package com.altech.electronic_store.services;


import com.altech.electronic_store.model.BasketItem;
import com.altech.electronic_store.model.Product;

import java.util.List;

public interface CustomerService {
    BasketItem addToBasket(Long productId, int quantity);
    void removeFromBasket(Long itemId);
    List<Product> filterProducts(String category, Double maxPrice, Double minPrice, Boolean available);

}
