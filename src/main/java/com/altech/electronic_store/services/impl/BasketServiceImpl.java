package com.altech.electronic_store.services.impl;

import com.altech.electronic_store.factory.DealStrategyFactory;
import com.altech.electronic_store.model.*;
import com.altech.electronic_store.repositories.BasketItemRepository;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.BasketService;
import com.altech.electronic_store.strategy.DealStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService{

    @Autowired private ProductRepository productRepo;
    @Autowired private BasketItemRepository basketRepo;
    @Autowired private DealRepository dealRepo;
    @Autowired private DealStrategyFactory strategyFactory;

    @Transactional
    @Override
    public void addToBasket(Long productId, int qty) {
        Product p = productRepo.findById(productId).orElseThrow();
        if (p.getStock() < qty) throw new RuntimeException("Insufficient stock");
        p.setStock(p.getStock() - qty);
        productRepo.save(p);
        basketRepo.save(new BasketItem(null, productId, qty));
    }

    public void removeFromBasket(Long itemId) {
        BasketItem item = basketRepo.findById(itemId).orElseThrow(
                () -> new RuntimeException("Item with id " + itemId + " not found")
        );
        Long productId = item.getProductId();
        int qty = item.getQuantity();
        Product p = productRepo.findById(productId).orElseThrow(
                () -> new RuntimeException("Product with id " + productId + " not found")
        );
        p.setStock(p.getStock() + qty);
        productRepo.save(p);
        basketRepo.deleteById(itemId);
    }

    @Transactional(readOnly = true)
    public Receipt calculateReceipt() {
        Receipt receipt = new Receipt();
        List<BasketItem> basketItems = basketRepo.findAll();
        Map<Long, Deal> activeDeals = dealRepo.findAll().stream()
                .filter(deal -> deal.getExpiration().isAfter(LocalDateTime.now()))
                .collect(Collectors.toMap(Deal::getProductId, Function.identity()));

        for (BasketItem item : basketItems) {
            Product product = productRepo.findById(item.getProductId()).orElseThrow(
                    () -> new RuntimeException("Product with id " + item.getProductId() + " not found")
            );
            Double unitPrice = product.getPrice();
            int qty = item.getQuantity();
            Double discount = 0.0;
            Deal deal = activeDeals.get(product.getId());
            if(deal != null && !deal.isExpired()) {
                DealStrategy strategy = strategyFactory.getStrategy(deal.getType());
                if(strategy != null){
                    discount = strategy.apply(product, qty);
                }
            }
//            if (activeDeals.containsKey(product.getId())) {
//                Deal deal = activeDeals.get(product.getId());
//                if ("BUY1GET50OFF".equals(deal.getType()) && qty >= 2) {
//                    int discountedItems = qty / 2;
//                    discount = unitPrice * 0.5 * discountedItems;
//                }
//            }

            Double finalPrice = unitPrice * qty - discount;
            DecimalFormat df = new DecimalFormat("#.##");
            finalPrice = Double.valueOf(df.format(finalPrice));
            ReceiptItem receiptItem = ReceiptItem.builder()
                    .productName(product.getName())
                    .quantity(qty)
                    .unitPrice(unitPrice)
                    .discount(discount)
                    .finalPrice(finalPrice)
                    .build();
            receipt.addItem(receiptItem);
        }
        return receipt;
    }


}

