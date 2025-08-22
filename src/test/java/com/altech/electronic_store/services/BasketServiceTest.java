package com.altech.electronic_store.services;

import com.altech.electronic_store.factory.DealStrategyFactory;
import com.altech.electronic_store.model.BasketItem;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.model.Receipt;
import com.altech.electronic_store.repositories.BasketItemRepository;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.impl.BasketServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class BasketServiceTest {
    @Mock private ProductRepository productRepo;
    @Mock private BasketItemRepository basketRepo;
    @Mock private DealRepository dealRepo;
    @InjectMocks private BasketServiceImpl basketService;

    @Test
    void testAddProductToBasket() {
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .category("Electronics")
                .price(1299.99)
                .stock(5)
                .available(true)
                .build();
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        basketService.addToBasket(1L, 2);

        assertEquals(3, product.getStock());
        verify(productRepo).save(product);
        verify(basketRepo).save(any(BasketItem.class));

    }

    @Test
    void testRemoveProductFromBasket() {
        Product product = Product.builder()
                .id(2L)
                .name("Laptop")
                .category("Electronics")
                .price(1299.99)
                .stock(5)
                .available(true)
                .build();
        BasketItem item = new BasketItem(10L, 2L, 1);

        when(basketRepo.findById(10L)).thenReturn(Optional.of(item));
        when(productRepo.findById(2L)).thenReturn(Optional.of(product));

        basketService.removeFromBasket(10L);

        assertEquals(6, product.getStock());
        verify(productRepo).save(product);
        verify(basketRepo).deleteById(10L);
    }

    @Test
    void testCalculateTotalWithMultipleProducts() {
        BasketItem item1 = new BasketItem(1L, 1L, 2);
        BasketItem item2 = new BasketItem(2L, 2L, 1);

        Product product1 = new Product(1L, "Monitor", "Electronics", 150.00, 10, true);
        Product product2 = new Product(2L, "Mouse", "Accessories", 50.00, 20, true);

        when(basketRepo.findAll()).thenReturn(List.of(item1, item2));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepo.findById(2L)).thenReturn(Optional.of(product2));

        Receipt total = basketService.calculateReceipt();

        assertEquals(350.00, total.getTotal()); // 2×150 + 1×50

    }

    @Test
    void testCalculateTotalWithEmptyBasket() {
        Receipt receipt = basketService.calculateReceipt();
        assertEquals(0.0, receipt.getTotal());
    }
}
