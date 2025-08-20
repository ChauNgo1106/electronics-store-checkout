package com.altech.electronic_store.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    private String name;
    private String category;
    private Double price;
    private int stock;
    private boolean available;
}
