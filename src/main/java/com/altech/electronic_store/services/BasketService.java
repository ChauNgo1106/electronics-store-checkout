package com.altech.electronic_store.services;

import com.altech.electronic_store.model.Receipt;

public interface BasketService {
    void addToBasket(Long productId, int quantity);
    void removeFromBasket(Long itemId);
    Receipt calculateReceipt();
}
