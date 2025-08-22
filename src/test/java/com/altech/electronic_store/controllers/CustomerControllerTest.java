package com.altech.electronic_store.controllers;

import com.altech.electronic_store.dto.request.BasketItemRequest;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.BasketService;
import com.altech.electronic_store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAddToBasket() throws Exception {
        BasketItemRequest basketItemRequest = BasketItemRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();
        mockMvc.perform(post("/api/v1/customer/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketItemRequest)))
                .andExpect(status().isOk());

        verify(basketService).addToBasket(1L, 2);
    }

    @Test
    void testRemoveFromBasket() throws Exception {
        mockMvc.perform(delete("/api/v1/customer/basket/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(basketService).removeFromBasket(1L);
    }

    @Test
    void testGetReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/customer/receipt"))
                .andExpect(status().isOk());

        verify(basketService).calculateReceipt();
    }

    @BeforeEach
    public void setup() {
        productRepository.save(new Product(null, "Laptop", "Electronics", 15000000.00,20,  true));
        productRepository.save(new Product(null, "Book", "Books", 200000.00, 40, true));
        productRepository.save(new Product(null, "Headphones", "Electronics", 50000.00, 10, false));
        productRepository.save(new Product(null, "Tablet", "Electronics", 7000000.00, 14, true));
        productRepository.save(new Product(null, "Notebook", "Books", 100000.00, 65, false));
    }

    @Test
    void testFilterApiWithOrConditions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/products/filter")
                        .param("category", "Books")
                        .param("minPrice", "100000")
                        .param("maxPrice", "500000")
                        .param("available", "false")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}

