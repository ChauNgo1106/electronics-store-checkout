package com.altech.electronic_store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private List<ReceiptItem> items = new ArrayList<>();
    private Double total = 0.0;

    public void addItem(ReceiptItem item) {
        items.add(item);
        total += item.getFinalPrice();
    }
}

