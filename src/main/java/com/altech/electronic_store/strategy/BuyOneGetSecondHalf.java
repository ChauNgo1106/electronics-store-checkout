package com.altech.electronic_store.strategy;

import com.altech.electronic_store.enums.DealType;
import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import org.springframework.stereotype.Component;

@Component(value = "BuyOneGetSecondHalf")
public class BuyOneGetSecondHalf implements DealStrategy{
    @Override
    public boolean support(DealType dealType) {
        return DealType.BUY_ONE_GET_SECOND_HALF.equals(dealType);
    }

    @Override
    public double apply(Product product, int quantity) {
        int eligiblePairs = quantity / 2;
        return eligiblePairs * product.getPrice() * 50 / 100;
    }
}
