package com.altech.electronic_store.services.impl;

import com.altech.electronic_store.model.Deal;
import com.altech.electronic_store.repositories.DealRepository;
import com.altech.electronic_store.services.DealService;
import org.springframework.stereotype.Service;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }
    @Override
    public Deal addDeal(Deal deal) {
        return dealRepository.save(deal);
    }
}
