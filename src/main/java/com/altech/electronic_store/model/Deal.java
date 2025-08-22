package com.altech.electronic_store.model;

import com.altech.electronic_store.enums.DealType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String type;
    private LocalDateTime expiration;

    public boolean isExpired() {
        return expiration != null && expiration.isBefore(LocalDateTime.now());
    }

}
