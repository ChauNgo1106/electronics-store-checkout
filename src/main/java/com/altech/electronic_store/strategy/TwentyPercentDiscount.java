package com.altech.electronic_store.strategy;

import com.altech.electronic_store.enums.DealType;
import com.altech.electronic_store.model.Product;
import org.springframework.stereotype.Component;

@Component(value = "TwentyPercentDiscount")
public class TwentyPercentDiscount implements DealStrategy{

    @Override
    public boolean support(DealType dealType) {
        return DealType.PERCENTAGE_DISCOUNT.equals(dealType);
    }

    @Override
    public double apply(Product product, int quantity) {
        return product.getPrice() * quantity * 20 / 100;
    }
}
