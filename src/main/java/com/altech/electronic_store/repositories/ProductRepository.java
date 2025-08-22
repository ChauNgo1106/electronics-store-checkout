package com.altech.electronic_store.repositories;

import com.altech.electronic_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:category IS NOT NULL AND LOWER(p.category) = LOWER(:category)) OR " +
            "(:minPrice IS NOT NULL AND :maxPrice IS NOT NULL AND p.price BETWEEN :minPrice AND :maxPrice) OR " +
            "(:available IS NOT NULL AND p.available = :available)")
    Page<Product> findWithOrFilters(
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("available") boolean available,
            Pageable pageable
    );

}
