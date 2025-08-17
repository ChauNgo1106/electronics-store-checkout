package com.altech.electronic_store.repositories;

import com.altech.electronic_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
