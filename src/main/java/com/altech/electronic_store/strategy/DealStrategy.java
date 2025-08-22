package com.altech.electronic_store.strategy;

import com.altech.electronic_store.enums.DealType;
import com.altech.electronic_store.model.Product;

public interface DealStrategy {
    boolean support(DealType dealType);
    double apply(Product product, int quantity);
}
