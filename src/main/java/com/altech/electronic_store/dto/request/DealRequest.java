package com.altech.electronic_store.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DealRequest {
    private Long productId;
    private String type;
    private BigDecimal discount;
    private LocalDateTime expiration;

    public boolean isExpired() {
        return expiration != null && expiration.isBefore(LocalDateTime.now());
    }
}
