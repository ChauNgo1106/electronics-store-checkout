package com.altech.electronic_store.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DealRequest {
    private Long productId;
    private String type;
    private LocalDateTime expiration;
}
