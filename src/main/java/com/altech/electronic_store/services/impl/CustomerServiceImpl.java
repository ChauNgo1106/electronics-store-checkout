package com.altech.electronic_store.services.impl;

import com.altech.electronic_store.model.BasketItem;
import com.altech.electronic_store.model.Product;
import com.altech.electronic_store.repositories.BasketItemRepository;
import com.altech.electronic_store.repositories.ProductRepository;
import com.altech.electronic_store.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;

    CustomerServiceImpl(ProductRepository productRepository, BasketItemRepository basketItemRepository){
        this.productRepository = productRepository;
        this.basketItemRepository = basketItemRepository;
    }

    @Override
    @Transactional
    public BasketItem addToBasket(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
        if(product.getStock() < quantity){
            throw new IllegalArgumentException("Not enough stock for product with id " + productId);
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        BasketItem basketItem = BasketItem.builder()
                .product(product)
                .quantity(quantity)
                .build();
        return basketItemRepository.save(basketItem);
    }

    @Override
    public void removeFromBasket(Long itemId) {
        basketItemRepository.deleteById(itemId);
    }

    @Override
    public List<Product> filterProducts(String category, Double maxPrice, Double minPrice, Boolean available) {
        return productRepository.findAll().stream()
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .filter(p -> available == null || p.isAvailable() == available)
                .collect(Collectors.toList());
    }

}
