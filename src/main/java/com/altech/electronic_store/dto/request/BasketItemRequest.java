package com.altech.electronic_store.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketItemRequest {
    private Long productId;
    private int quantity;
}
