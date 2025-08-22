package com.altech.electronic_store.services;

import com.altech.electronic_store.dto.request.DealRequest;
import com.altech.electronic_store.enums.DealType;
import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.impl.DealServiceImpl;
import com.altech.electronic_store.services.impl.ProductServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DealServiceTest {

    @Mock
    private DealRepository dealRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @InjectMocks
    private DealServiceImpl dealService;

    @Test
    public void testCreateDeal() {
        Product product = Product.builder()
                .name("Laptop")
                .category("Electronics")
                .price(999.99)
                .stock(10)
                .available(true)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productRepository.save(product);
        Deal deal = new Deal();
        deal.setProductId(savedProduct.getId());
        deal.setType("BuyOneGetSecondHalf");
        deal.setExpiration(LocalDateTime.now().plusDays(2));
        when(dealRepository.save(any(Deal.class))).thenReturn(deal);

        Deal savedDeal = null;
        try {
            savedDeal = dealService.addDeal(deal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("BuyOneGetSecondHalf", savedDeal.getType());
        assertTrue(savedDeal.getExpiration().isAfter(LocalDateTime.now()));
    }

    @Test
    void testAddExpiredDealThrowsException() {
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .category("Electronics")
                .price(999.99)
                .stock(10)
                .available(true)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productRepository.save(product);
        Deal expiredDeal = new Deal();
        expiredDeal.setProductId(savedProduct.getId());
        expiredDeal.setType("BuyOneGetSecondHalf");
        expiredDeal.setExpiration(LocalDateTime.now().minusDays(1)); // expired

        assertThrows(Exception.class, () -> {
            dealService.addDeal(expiredDeal);
        });

        verify(dealRepository, never()).save(any(Deal.class));
    }
}
