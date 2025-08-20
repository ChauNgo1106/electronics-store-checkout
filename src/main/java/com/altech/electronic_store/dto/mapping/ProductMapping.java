package com.altech.electronic_store.dto.mapping;

import com.altech.electronic_store.dto.request.ProductRequest;
import com.altech.electronic_store.model.Product;

public class ProductMapping {
    public static Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .category(productRequest.getCategory())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .available(productRequest.isAvailable())
                .build();
    }
}
