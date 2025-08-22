package com.altech.electronic_store.dto.request;

import lombok.Data;

@Data
public class BasketItemRequest {
    private Long productId;
    private int quantity;
}
