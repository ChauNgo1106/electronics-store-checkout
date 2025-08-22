package com.altech.electronic_store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItem {
    private String productName;
    private int quantity;
    private Double unitPrice;
    private Double discount;
    private Double finalPrice;
}

