package com.altech.electronic_store.controllers;

import com.altech.electronic_store.dto.request.BasketItemRequest;
import com.altech.electronic_store.model.BasketItem;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.services.BasketService;
import com.altech.electronic_store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @MockBean
    private ProductService productService;

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
}

