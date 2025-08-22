package com.altech.electronic_store.services.impl;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.services.DealService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }
    @Override
    public Deal addDeal(Deal deal) throws Exception {
        if (deal.isExpired()) {
            throw new Exception("Deal expiration date must be in the future");
        }
        return dealRepository.save(deal);
    }
}
