package com.example.messageBroker.core.Exchange.ExchangeStrategy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.messageBroker.domain.Constants.ExchangeType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoutingStrategyResolver {
    
    private final List<RoutingStrategy> strategies;

    public RoutingStrategy resolve(ExchangeType type){
        return this.strategies.stream().filter(s -> s.supports(type)).findFirst().orElseThrow();
    }

}
