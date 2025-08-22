package com.altech.electronic_store.factory;

import com.altech.electronic_store.strategy.DealStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
//dynamycally map deals type to their logic
public class DealStrategyFactory {
    private final Map<String, DealStrategy> strategies;

    @Autowired
    public DealStrategyFactory(List<DealStrategy> strategyList) {
        strategies = strategyList.stream()
                .collect(Collectors.toMap(s -> s.getClass().getAnnotation(Component.class).value(), Function.identity()));
    }

    public DealStrategy getStrategy(String type) {
        return strategies.get(type);
    }
}
