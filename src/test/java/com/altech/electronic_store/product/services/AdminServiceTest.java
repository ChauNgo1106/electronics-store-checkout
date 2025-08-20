package com.altech.electronic_store.product.services;


import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.impl.AdminServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    public AdminServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    //create a new product testing
    @Test
    void testCreateProduct() {
        Product product = Product.builder()
                .name("Laptop")
                .category("Electronics")
                .price(BigDecimal.valueOf(999.99))
                .stock(10)
                .available(true)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = adminService.createProduct(product);
        assertEquals("Laptop", savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testCreateProductWithNull(){
        Product product = null;
        Product savedProduct = adminService.createProduct(product);
        assertNull(savedProduct);
    }

    @Test
    void testCreateProductWithZeroAvailable(){
        Product product = Product.builder()
                .name("Iphone")
                .category("Electronics")
                .price(BigDecimal.valueOf(999.99))
                .stock(0)
                .available(false)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = adminService.createProduct(product);
        assertFalse( savedProduct.isAvailable());
        verify(productRepository, times(1)).save(product);
    }

    // delete product by ID testing
    @Test
    void testDeleteProduct(){
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .category("Electronics")
                .price(BigDecimal.valueOf(999.99))
                .stock(10)
                .available(true)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = adminService.createProduct(product);

        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);
        adminService.deleteProduct(productId);

        Optional<Product> deletedProduct = productRepository.findById(productId);
        assertTrue(deletedProduct.isEmpty());
        verify(productRepository, times(1)).deleteById(productId);
    }

    //REWORK-testing later.
    @Test
    @Transactional //roll back after the testing
    public void testAddDealToProduct(){
        Product product = Product.builder()
                .name("Smartphone")
                .category("Electronics")
                .price(BigDecimal.valueOf(699.99))
                .stock(30)
                .available(true)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = adminService.createProduct(product);

        Deal deal = Deal.builder()
                .type("PERCENTAGE_DISCOUNT")
                .discount(BigDecimal.valueOf(0.2))
                .expiration(LocalDateTime.now().plusDays(7))
                .build();
        when(dealRepository.save(any(Deal.class))).thenReturn(deal);
        Deal savedDeal = adminService.addDeal(1L, "PERCENTAGE_DISCOUNT", LocalDateTime.now().plusDays(7));

        assertEquals("PERCENTAGE_DISCOUNT", savedDeal.getType());
        assertEquals(BigDecimal.valueOf(0.2), savedDeal.getDiscount());
        assertTrue(savedDeal.getExpiration().isAfter(LocalDateTime.now()));
        verify(dealRepository, times(1)).save(any(Deal.class));
        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    public void testAddExpiredDealFails(){
        Deal deal = Deal.builder()
                .type("PERCENTAGE_DISCOUNT")
                .discount(BigDecimal.valueOf(0.2))
                .expiration(LocalDateTime.now().minusDays(1))
                .build();
        Deal savedDeal = dealRepository.save(deal);
        assertTrue(savedDeal.getExpiration().isBefore(LocalDateTime.now()));
    }

    @Test
    public void testExpiredDealIsDetected() {
        Deal deal = new Deal();
        deal.setExpiration(LocalDateTime.now().minusHours(1));
        Assertions.assertTrue(deal.isExpired());
    }

}
