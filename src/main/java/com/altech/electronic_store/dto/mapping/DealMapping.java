package com.altech.electronic_store.dto.mapping;

import com.altech.electronic_store.dto.request.DealRequest;
import com.altech.electronic_store.model.Deal;

public class DealMapping {
    public static Deal toDeal(DealRequest dealRequest){
        return Deal.builder()
                .type(dealRequest.getType())
                .discount(dealRequest.getDiscount())
                .expiration(dealRequest.getExpiration())
                .build();
    }
}
