package com.altech.electronic_store.product;


import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.ProductService;
import com.altech.electronic_store.services.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = Product.builder()
                .name("Laptop")
                .category("Electronics")
                .price(BigDecimal.valueOf(999.99))
                .stock(10)
                .available(true)
                .build();
        //- This tells the test: “Whenever productRepository.save()
        // is called with any Product, return our expected product.”
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productService.createProduct(product);
        assertEquals("Laptop", savedProduct.getName());
        //- This confirms that productRepository.save()
        // was called exactly once with the input product.
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testCreateProductWithNull(){
        Product product = null;
        Product savedProduct = productService.createProduct(product);
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
        Product savedProduct = productService.createProduct(product);
        assertFalse( savedProduct.isAvailable());
        verify(productRepository, times(1)).save(product);
    }
}
